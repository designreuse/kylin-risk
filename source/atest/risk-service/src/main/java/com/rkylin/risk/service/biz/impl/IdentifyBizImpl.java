package com.rkylin.risk.service.biz.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.facerecognition.api.service.FaceRecognitionService;
import com.rkylin.facerecognition.api.service.LinkFaceOCRService;
import com.rkylin.facerecognition.api.vo.BankCardEntity;
import com.rkylin.facerecognition.api.vo.BaseResultVo;
import com.rkylin.facerecognition.api.vo.IdCardEntity;
import com.rkylin.facerecognition.api.vo.ImageEntity;
import com.rkylin.risk.core.entity.IdentifyRecord;
import com.rkylin.risk.core.service.IdentifyRecordService;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.IdentifyBiz;
import com.rkylin.risk.service.biz.PengYuanCreditBiz;
import com.rkylin.risk.service.utils.DateUtil;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * OCR
 *
 * @author qiuxian
 * @create 2016-08-29 11:38
 **/
@Component("identifyBiz")
@Slf4j
public class IdentifyBizImpl implements IdentifyBiz {
  @Resource
  private LinkFaceOCRService linkFaceOCRService;
  @Resource
  private FaceRecognitionService faceRecognitionService;
  @Resource
  private IdentifyRecordService identifyRecordService;
  @Resource
  private PengYuanCreditBiz pengYuanCreditBiz;
  @Value("${photoRecognition.appId}")
  private String appId;
  @Value("${photoRecognition.appKey}")
  private String appKey;
  @Value("${photoRecognition.appSecret}")
  private String appSecret;
  @Resource
  private ObjectMapper jsonMapper;

  private void setAttachValues() {
    RpcContext.getContext().setAttachment("appId", appId);
    RpcContext.getContext().setAttachment("appKey", appKey);
    RpcContext.getContext().setAttachment("appSecret", appSecret);
  }

  public String requestFaceresult(Map<String, Object> imageEntityMap,
      PersonFactor factor) {
    StringBuffer sbf = new StringBuffer();
    ImageEntity imageEntity1 = (ImageEntity) imageEntityMap.get("idcard");
    ImageEntity imageEntity2 = (ImageEntity) imageEntityMap.get("idcardBack");
    ImageEntity imageEntity3 = (ImageEntity) imageEntityMap.get("idcardPersonPic");
    ImageEntity imageEntity4 = (ImageEntity) imageEntityMap.get("bankCard");

    //身份证正反面
    Map<String, Object> map1 =
        requestIdcerdinfo(factor, imageEntityMap.get("idcardmd5check").toString(),
            imageEntityMap.get("idcardBackmd5check").toString(), imageEntity1, imageEntity2);
    if (map1.get("info") != null) {
      IdCardEntity.Info info = (IdCardEntity.Info) map1.get("info");
      //鹏元征信验证身份证
      Map<String, String> creditPymap =
          pengYuanCreditBiz.creditPY(info.getName(), info.getNumber());
      if (creditPymap.get("result") != null && "error".equals(creditPymap.get("result"))) {
        sbf.append(map1.get("errormsg"));
        return sbf.toString();
      } else {
        sbf.append(
            creditPymap.get("warning") == null ? "" : creditPymap.get("warning").toString());
      }
    }
    sbf.append(map1.get("message") == null ? "" : map1.get("message"));
    //银行卡识别, 悦视觉渠道不走银行卡方面的识别
    if (!RuleConstants.YUESHIJUE_CHANNEL_ID.equals(factor.getConstid())
        && !RuleConstants.MEIRONG_CHANNEL_ID.equals(factor.getConstid())
        && !RuleConstants.LVYOU_CHANNEL_ID.equals(factor.getConstid())) {
      Map<String, Object> map2 =
          requestBankcard(factor, imageEntityMap.get("bankCardmd5check").toString(), imageEntity4);
      sbf.append(map2.get("message") == null ? "" : map2.get("message").toString());
    }
    //个人手持身份证照片与身份证反面照片比对
    Map<String, Object> map3 =
        requestPersonPic(factor, imageEntityMap.get("idcardmd5check").toString(),
            imageEntityMap.get("idcardPersonPicmd5check").toString(),
            imageEntity1, imageEntity3);
    sbf.append(map3.get("message") == null ? "" : map3.get("message").toString());
    return sbf.toString();
  }


  public String requestYSJFaceresult(Map<String, Object> imageEntityMap,
      PersonFactor factor) {
    StringBuffer sbf = new StringBuffer();
    ImageEntity imageEntity1 = (ImageEntity) imageEntityMap.get("idcard");
    ImageEntity imageEntity2 = (ImageEntity) imageEntityMap.get("idcardBack");


    //身份证正反面
    Map<String, Object> map1 =
        requestIdcerdinfo(factor, imageEntityMap.get("idcardmd5check").toString(),
            imageEntityMap.get("idcardBackmd5check").toString(), imageEntity1, imageEntity2);
    if (map1.get("info") != null) {
      IdCardEntity.Info info = (IdCardEntity.Info) map1.get("info");
      //鹏元征信验证身份证
      Map<String, String> creditPymap =
          pengYuanCreditBiz.creditPY(info.getName(), info.getNumber());
      if (creditPymap.get("result") != null && "error".equals(creditPymap.get("result"))) {
        sbf.append(map1.get("errormsg"));
        return sbf.toString();
      } else {
        sbf.append(
            creditPymap.get("warning") == null ? "" : creditPymap.get("warning").toString());
      }
    }
    sbf.append(map1.get("message") == null ? "" : map1.get("message"));
    return sbf.toString();
  }

  //身份证正反面接口
  private Map<String, Object> requestIdcerdinfo(PersonFactor factor, String idcardmd5check1,
      String idcardmd5check2,
      ImageEntity imageEntity1, ImageEntity imageEntity2) {
    Map<String, Object> returnMap = new HashMap<>();
    StringBuffer message = new StringBuffer();
    try {
      setAttachValues();
      IdentifyRecord identifyRecord =
          getIdentifyRecord("getIdCardInfo", idcardmd5check1, idcardmd5check2, factor.getUserid());
      BaseResultVo<IdCardEntity> idcardresult = new BaseResultVo<>();
      if (identifyRecord != null && "success".equals(identifyRecord.getStatus())) {
        log.info("-----身份证正反面识别记录存在");
        idcardresult.setCode(identifyRecord.getCode());
        IdCardEntity entity = new IdCardEntity();
        IdCardEntity.Info info = entity.new Info();
        info.setName(identifyRecord.getName());
        info.setNumber(identifyRecord.getNumber());
        info.setAddress(identifyRecord.getAddress());
        info.setAuthority(identifyRecord.getAuthority());
        info.setSex(identifyRecord.getSex());
        info.setYear(identifyRecord.getYear());
        info.setMonth(identifyRecord.getMonth());
        info.setDay(identifyRecord.getDay());
        info.setTimelimit(identifyRecord.getTimelimit());
        entity.setInfo(info);
        idcardresult.setResultData(entity);
        idcardresult.setSuccess(true);
      } else {
        log.info("身份证正反面接口开始----------------------");
        idcardresult = linkFaceOCRService.getIdCardInfo(factor.getConstid(), imageEntity1,
            imageEntity2);
        log.info("身份证正反面接口结束----------------------");
        if (idcardresult.isSuccess()) {
          IdentifyRecord record = new IdentifyRecord();
          record.setCustomerid(factor.getUserid());
          record.setInterfacename("getIdCardInfo");
          record.setMd5checksum1(idcardmd5check1);
          record.setMd5checksum2(idcardmd5check2);
          record.setStatus("success");
          record.setCode(idcardresult.getCode());
          record.setName(idcardresult.getResultData().getInfo().getName());
          record.setNumber(idcardresult.getResultData().getInfo().getNumber().toUpperCase());
          record.setSex(idcardresult.getResultData().getInfo().getSex());
          record.setTimelimit(idcardresult.getResultData().getInfo().getTimelimit());
          record.setAuthority(idcardresult.getResultData().getInfo().getAuthority());
          record.setAddress(idcardresult.getResultData().getInfo().getAddress());
          record.setYear(idcardresult.getResultData().getInfo().getYear());
          record.setMonth(idcardresult.getResultData().getInfo().getMonth());
          record.setDay(idcardresult.getResultData().getInfo().getDay());
          record.setCheckorderid(factor.getCheckorderid());
          identifyRecordService.insert(record);
        }
      }
      log.info("idcardresult:" + idcardresult.toString());
      if (idcardresult.isSuccess()) {
        IdCardEntity iden = (IdCardEntity) idcardresult.getResultData();
        IdCardEntity.Info info = iden.getInfo();

        returnMap.put("info", info);
        String certificatestartdate = info.getTimelimit().substring(0, 8);
        String certificateexpiredate = info.getTimelimit().substring(9, 17);
        String timebegin_year = certificatestartdate.substring(0, 4);
        String timebegin_month = certificatestartdate.substring(4, 6);
        String timebegin_day = certificatestartdate.substring(6, 8);
        String timeend = certificateexpiredate.substring(0, 4);
        if (StringUtils.isEmpty(info.getName()) || !info.getName().equals(factor.getUsername())) {
          message.append("身份证姓名不匹配;");
        }
        if (StringUtils.isEmpty(info.getNumber()) || !info.getNumber().toUpperCase()
            .equals(factor.getCertificatenumber().toUpperCase())) {
          log.info("照片识别的身份证号码:"
              + info.getNumber()
              + "----------PersonFactor身份证号码:"
              + factor.getCertificatenumber());
          message.append("身份证号码不匹配;");
        }

        if (StringUtils.isNotEmpty(factor.getCertificateexpiredate())
            && !factor.getCertificateexpiredate().replace("-", "").equals(certificateexpiredate)) {
          log.info("身份证失效日期不匹配:info.getTimelimit()-->"
              + info.getTimelimit()
              + ";certificateexpiredate:"
              + factor.getCertificateexpiredate());
          message.append("身份证失效日期不匹配;");
        }

        if (DateUtil.toLocalDate(certificateexpiredate, "yyyyMMdd").compareTo(LocalDate.now())
            < 1) {
          log.info("身份证过期");
          message.append("身份证过期;");
        }
        int idcardyears = Integer.parseInt(timeend) - Integer.parseInt(timebegin_year);
        //生效日期
        LocalDate nowdate =
            new LocalDate(Integer.parseInt(timebegin_year), Integer.parseInt(timebegin_month),
                Integer.parseInt(timebegin_day));
        //身份证识别的日期
        int age;
        if (info.getMonth() != null && info.getDay() != null) {
          LocalDate birthdate =
              new LocalDate(Integer.parseInt(info.getYear()), Integer.parseInt(info.getMonth()),
                  Integer.parseInt(info.getDay()));
          age = Years.yearsBetween(birthdate, nowdate).getYears();
        } else {
          age = Integer.parseInt(timebegin_year) - Integer.parseInt(info.getYear());
        }
        //小于十六周岁的，发给有效期五年的居民身份证；
        if (age < 16 && idcardyears != 5) {
          log.info("age:" + age + "----------idcardyears:" + idcardyears);
          message.append("身份证有效期不合法;");
        }
        //十六周岁至二十五周岁的，发给有效期十年的居民身份证；
        if ((age >= 16 && age <= 25) && idcardyears != 10) {
          log.info("age:" + age + "----------idcardyears:" + idcardyears);
          message.append("身份证有效期不合法;");
        }
        //二十六周岁至四十五周岁的，发给有效期二十年的居民身份证；
        if ((age > 25 && age <= 45) && idcardyears != 20) {
          log.info("age:" + age + "----------idcardyears:" + idcardyears);
          message.append("身份证有效期不合法;");
        }
      } else {
        message.append(getMessage(idcardresult) + ";");
      }
    } catch (Exception e) {
      message.append("识别身份证正反面图片接口异常;");
      log.info("识别身份证正反面图片接口异常;");
      e.printStackTrace();
    }
    returnMap.put("message", message.toString());
    return returnMap;
  }

  private Map<String, Object> requestBankcard(PersonFactor factor, String md5check,
      ImageEntity imageEntity4) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    StringBuffer message = new StringBuffer();
    try {
      //银行卡
      setAttachValues();
      IdentifyRecord identifyRecord =
          getIdentifyRecord("getBankCardInfo", md5check, null, factor.getUserid());
      BaseResultVo<BankCardEntity> bankresult = new BaseResultVo<BankCardEntity>();
      if (identifyRecord != null && "success".equals(identifyRecord.getStatus())) {
        log.info("-----银行卡识别记录存在");
        bankresult.setCode(identifyRecord.getCode());
        BankCardEntity entity = new BankCardEntity();
        BankCardEntity.Result info = entity.new Result();
        info.setCard_number(identifyRecord.getCardnumber());
        info.setBank_name(identifyRecord.getBankname());
        info.setCard_type(identifyRecord.getCardtype());
        entity.setResult(info);
        bankresult.setResultData(entity);
        bankresult.setSuccess(true);
      } else {
        log.info("银行卡接口开始----------------------");
        bankresult = linkFaceOCRService.getBankCardInfo(factor.getConstid(), imageEntity4);
        log.info("银行卡接口结束----------------------");
        if (bankresult.isSuccess()) {
          IdentifyRecord record = new IdentifyRecord();
          record.setCustomerid(factor.getUserid());
          record.setInterfacename("getBankCardInfo");
          record.setMd5checksum1(md5check);
          record.setStatus("success");
          record.setCode(bankresult.getCode());
          record.setBankname(bankresult.getResultData().getResult().getBank_name());
          record.setCardnumber(bankresult.getResultData().getResult().getCard_number());
          record.setCardtype(bankresult.getResultData().getResult().getCard_type());
          record.setCheckorderid(factor.getCheckorderid());
          identifyRecordService.insert(record);
        }
      }
      log.info("-------bankresult:" + bankresult.toString());
      if (bankresult.isSuccess()) {
        if ("中国工商银行".equals(bankresult.getResultData().getResult().getBank_name())) {
          message.append("银行卡为工商银行;");
        }
        if (StringUtils.isEmpty(factor.getBankname())) {
          message.append("rop未查询到客户的账户银行卡信息;");
        }
        if (StringUtils.isNotEmpty(factor.getBankname()) && !factor.getBankname()
            .equals(bankresult.getResultData().getResult().getBank_name())) {
          message.append("OCR识别的银行名称与查询到的银行名称不匹配;");
        }
        if (!factor.getTcaccount()
            .equals(bankresult.getResultData().getResult().getCard_number())) {
          message.append("OCR识别的银行卡号与个人信息的银行卡号不匹配;");
        }
      } else {
        message.append(getMessage(bankresult) + ";");
      }
    } catch (Exception e) {
      message.append("识别银行卡图片接口异常;");
      log.info("识别银行卡图片接口异常{}", e);
      e.printStackTrace();
    }
    returnMap.put("message", message);
    return returnMap;
  }

  private Map<String, Object> requestPersonPic(PersonFactor factor, String idcardmd5check1,
      String idcardmd5check2,
      ImageEntity imageEntity1, ImageEntity imageEntity3) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    String message = "";
    try {
      IdentifyRecord identifyRecord =
          getIdentifyRecord("photoMatchIdCard", idcardmd5check1, idcardmd5check2,
              factor.getUserid());
      BaseResultVo<String> personresult = new BaseResultVo<String>();
      if (identifyRecord != null && "success".equals(identifyRecord.getStatus())) {
        log.info("-----个人照片与身份证对比记录存在");
        personresult.setCode(identifyRecord.getCode());
        personresult.setResultData(identifyRecord.getSimilarity());
        personresult.setSuccess(true);
        message = "个人照片与身份证照片相似度：" + identifyRecord.getSimilarity();
      } else {
        //个人照片与身份证对比
        setAttachValues();
        log.info("个人照片与身份证对比开始----------------------");
        personresult = faceRecognitionService.photoMatchIdCard(factor.getConstid(), imageEntity1,
            imageEntity3);
        log.info("个人照片与身份证对比结束----------------------");
        log.info("personresult:" + personresult.toString());
        if (personresult.isSuccess()) {
          returnMap.put("personpicresult", personresult.getResultData());
          IdentifyRecord record = new IdentifyRecord();
          record.setCustomerid(factor.getUserid());
          record.setStatus("success");
          record.setCode(personresult.getCode());
          record.setInterfacename("photoMatchIdCard");
          record.setMd5checksum1(idcardmd5check1);
          record.setMd5checksum2(idcardmd5check2);
          record.setCheckorderid(factor.getCheckorderid());
          record.setSimilarity(personresult.getResultData());
          identifyRecordService.insert(record);
          message = "个人照片与身份证照片相似度：" + personresult.getResultData();
          log.info(message);
        } else {
          message = getMessage(personresult) + ";";
        }
      }
    } catch (Exception e) {
      message = "个人照片与身份证照片比对接口异常;";
      log.info("识别银行卡图片接口异常个人照片与身份证照片比对接口异常 {}", e);
    }
    returnMap.put("message", message);
    return returnMap;
  }

  private IdentifyRecord getIdentifyRecord(String interfacename, String md5check1, String md5check2,
      String customerid) {
    IdentifyRecord identifyRecord = new IdentifyRecord();
    identifyRecord.setInterfacename(interfacename);
    identifyRecord.setCustomerid(customerid);
    if ("getBankCardInfo".equals(interfacename)) {
      identifyRecord.setMd5checksum1(md5check1);
    } else if ("rop_accountinfo".equals(interfacename)) {
    } else {
      identifyRecord.setMd5checksum1(md5check1);
      identifyRecord.setMd5checksum2(md5check2);
    }
    IdentifyRecord returnrecord = identifyRecordService.queryOne(identifyRecord);
    return returnrecord;
  }

  private static Map<String, String> getResponseCode() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("ENCODING_ERROR", "参数非UTF-8编码;");
    map.put("DOWNLOAD_TIMEOUT", "网络地址图片获取超时,详情见字段 image;");
    map.put("DOWNLOAD_ERROR", "网络地址图片获取失败，详情见字段 image;");
    map.put("IMAGE_FILE_SIZE_TOO_BIG", "图片体积过大，详情见字段 image;");
    map.put("IMAGE_ID_NOT_EXIST", "图片不存在，详情见字段 image;");
    map.put("NO_FACE_DETECTED", "图片未检测出人脸 ，详情见字段 image;");
    map.put("CORRUPT_IMAGE", "不是图片文件或已经损坏，详情见字段 image;");
    map.put("INVALID_IMAGE_FORMAT_OR_SIZE", "图片大小或格式不符合要求，详情见字段 image;");
    map.put("INVALID_ARGUMENT", "请求参数错误，具体原因见 reason 字段内容;");
    map.put("UNAUTHORIZED", "账号或密钥错误;");
    map.put("KEY_EXPIRED", "账号过期，具体情况见 reason 字段内容;");
    map.put("RATE_LIMIT_EXCEEDED", "调用频率超出限额;");
    map.put("NO_PERMISSION", "无调用权限;");
    map.put("NOT_FOUND", "请求路径错误;");
    map.put("INTERNAL_ERROR", "服务器内部错误;");
    return map;
  }

  private String getMessage(BaseResultVo clas) {
    String mes = "";
    if (clas.getCode().contains("400")
        || clas.getCode().contains("401")
        || clas.getCode().contains("403")
        || clas.getCode().contains("404")
        || clas.getCode().contains("500")) {
      try {
        JsonNode jsonNode = jsonMapper.readTree(clas.getErrorMsg());
        String status = jsonNode.path("status").asText();
        mes =
            getResponseCode().get(status) == null ? clas.getErrorMsg()
                : getResponseCode().get(status);
      } catch (Exception e) {
        mes = clas.getErrorMsg();
      }
    } else {
      mes = clas.getErrorMsg();
    }
    return mes;
  }
}
