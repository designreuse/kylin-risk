package com.rkylin.risk.service.biz.impl;

import com.Rop.api.request.FsFileurlGetRequest;
import com.Rop.api.response.FsFileurlGetResponse;
import com.alibaba.dubbo.rpc.RpcContext;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rkylin.facerecognition.api.service.FaceRecognitionService;
import com.rkylin.facerecognition.api.service.LinkFaceOCRService;
import com.rkylin.facerecognition.api.vo.BankCardEntity;
import com.rkylin.facerecognition.api.vo.BaseResultVo;
import com.rkylin.facerecognition.api.vo.IdCardEntity;
import com.rkylin.facerecognition.api.vo.ImageEntity;
import com.rkylin.gateway.dto.CommonDto;
import com.rkylin.gateway.dto.personBaseInfo.PersonBaseInfoRespDto;
import com.rkylin.gateway.dto.phoneNoCheck.PhoneNoCheckDto;
import com.rkylin.gateway.dto.phoneNoCheck.PhoneNoCheckRespDto;
import com.rkylin.gateway.enumtype.TransCode;
import com.rkylin.gateway.service.CreditService;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.entity.IdentifyRecord;
import com.rkylin.risk.core.service.IdentifyRecordService;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.IdentifyBiz;
import com.rkylin.risk.service.utils.DateUtil;
import com.rkylin.risk.service.utils.MulThreadRSAUtils;
import com.rkylin.risk.service.utils.ROPUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 身份证识别
 *
 * @author qiuxian
 * @create 2016-08-29 11:38
 **/
@Component("identifyBiz")
@Slf4j
public class IdentifyBizImpl implements IdentifyBiz {

  @Resource
  private ROPUtil ropUtil;
  @Resource
  private LinkFaceOCRService linkFaceOCRService;
  @Resource
  private FaceRecognitionService faceRecognitionService;
  @Resource
  private CreditService creditService;
  @Resource
  private IdentifyRecordService identifyRecordService;

  @Value("${decode.privateKey}")
  private String privateKey;
  @Value("${photoRecognition.appId}")
  private String appId;
  @Value("${photoRecognition.appKey}")
  private String appKey;
  @Value("${photoRecognition.appSecret}")
  private String appSecret;

  @Value("${credit.py.key}")
  private String pyKey;
  @Value("${credit.py.busiNo}")
  private String busiNo;
  @Value("${credit.py.orgNo}")
  private String orgNo;

  private static Cache<String, Key> cache = CacheBuilder.newBuilder().maximumSize(5).build();

  @Override
  public Map<String, Object> downloadFile(PersonFactor personFactor) {
    //下载
    Map<String, Object> returnMap = new HashMap<String, Object>();
    String fileUrl = "";
    FsFileurlGetRequest fileurlRequest = new FsFileurlGetRequest();
    //fileurlRequest.setUrl_key(personFactor.getUrlkey());
    try {
      FsFileurlGetResponse filrUrlResponse = ropUtil.getPhotoResponse(fileurlRequest, "json");
      if (filrUrlResponse != null && filrUrlResponse.getFile_url() != null) {
        fileUrl = filrUrlResponse.getFile_url();
       // log.info("['" + personFactor.getUrlkey() + "'文件查询成功：] " + filrUrlResponse.getBody());
      } else {
        returnMap.put("downloadresult", "false");
        returnMap.put("downloadmsg", "下载失败，系统异常");
        //log.info("['" + personFactor.getUrlkey() + "'文件下载失败：] " + filrUrlResponse);
        return returnMap;
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.info(e.toString());
    }
    InputStream inputStream = null;
    try {
      URL url = new URL(fileUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      //设置超时间为3秒
      conn.setConnectTimeout(3 * 1000);
      //防止屏蔽程序抓取而返回403错误
      conn.setRequestProperty("User-Agent",
          "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
      //得到输入流
      inputStream = conn.getInputStream();
      //获取自己数组
      log.info("开始读取数据流");
      byte[] encodedData = IOUtils.toByteArray(inputStream);
      log.info("读取数据流完成");
      byte[] dencodedDate;
      //私钥解密
      log.info("开始解密");
      Key rsaPrivateKey = cache.get(privateKey, new Callable<Key>() {
        @Override public Key call() throws Exception {
          return MulThreadRSAUtils.generatePrivatekey(privateKey);
        }
      });
      dencodedDate = MulThreadRSAUtils.multiThreadDecryp(encodedData, 5, rsaPrivateKey);
      log.info("解密完成");
      Map<String, Object> imageEntityMap = unzip(dencodedDate);
      if (imageEntityMap.get("idcardmd5check") == null
          || imageEntityMap.get("idcardBackmd5check") == null
          || imageEntityMap.get("bankCardmd5check") == null) {
        log.info("存在照片的MD5校验码为空]");
        returnMap.put("downloadresult", "exception");
        returnMap.put("downloadmsg", "请按照要求上传照片");
      } else {
        returnMap = requestFaceresult(imageEntityMap, personFactor);
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.info(e.toString());
      returnMap.put("downloadresult", "exception");
      returnMap.put("downloadmsg", "解析照片异常");
     // log.info("['" + personFactor.getUrlkey() + "'解析照片异常]");
    } finally {
      try {
        if (inputStream != null) {
          inputStream.close();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    return returnMap;
  }

  //解压文件
  private Map<String, Object> unzip(byte[] zipdatas) throws IOException {
    Map<String, Object> imageResult = new HashMap<>();
    ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipdatas));
    ZipEntry zipEntry = zipInputStream.getNextEntry();
    try {
      while (zipEntry != null) {

        byte[] encodedData = null;
        if (!zipEntry.isDirectory()) {
          String fileName = zipEntry.getName();
          System.out.println(fileName);
          encodedData = IOUtils.toByteArray(zipInputStream);
          String md5CheckSum = DigestUtils.md5Hex(encodedData);

          ImageEntity entity = new ImageEntity();
          entity.setType(ImageEntity.Type.BYTES);
          entity.setData(encodedData);
          String name = zipEntry.getName();
          log.info(name + "大小：" + new Amount(zipEntry.getSize()).divide(1024) + "kb");
          name = name.substring(0, name.indexOf("."));
          switch (name) {
            case "idcard":
              imageResult.put("idcard", entity);
              imageResult.put("idcardmd5check", md5CheckSum);
              break;
            case "idcard_back":
              imageResult.put("idcardBack", entity);
              imageResult.put("idcardBackmd5check", md5CheckSum);
              break;
            case "idcard_person_pic":
              imageResult.put("idcardPersonPic", entity);
              imageResult.put("idcardPersonPicmd5check", md5CheckSum);
              break;
            case "bankcard":
              imageResult.put("bankCard", entity);
              imageResult.put("bankCardmd5check", md5CheckSum);
              break;
            default:
              System.out.println("no photp");
          }
        }
        zipEntry = zipInputStream.getNextEntry();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return imageResult;
  }

  private void setAttachValues() {
    System.out.println("appId:" + appId + ";appKey:" + appKey + ";appSecret:" + appSecret);
    RpcContext.getContext().setAttachment("appId", appId);
    RpcContext.getContext().setAttachment("appKey", appKey);
    RpcContext.getContext().setAttachment("appSecret", appSecret);
  }

  private CommonDto setBasicInfo(CommonDto dto) {
    dto.setSysNo(busiNo);
    dto.setTransCode(TransCode.PERSON_BASIC_INFO.getTransCode());
    dto.setOrgNo(orgNo);
    dto.setSignType(1);
    String singMessage = dto.sign(pyKey);
    dto.setSignMsg(singMessage);
    return dto;
  }

  private Map<String, Object> requestFaceresult(Map<String, Object> imageEntityMap,
      PersonFactor factor) {
    StringBuffer sbf = new StringBuffer();
    Map<String, Object> returnMap = new HashMap<>();
    ImageEntity imageEntity1 = (ImageEntity) imageEntityMap.get("idcard");
    ImageEntity imageEntity2 = (ImageEntity) imageEntityMap.get("idcardBack");
    ImageEntity imageEntity3 = (ImageEntity) imageEntityMap.get("idcardPersonPic");
    ImageEntity imageEntity4 = (ImageEntity) imageEntityMap.get("bankCard");
    Map<String, Object> map1 =
        requestIdcerdinfo(factor, imageEntityMap.get("idcardmd5check").toString(),
            imageEntityMap.get("idcardBackmd5check").toString(), imageEntity1, imageEntity2);
    if (map1.get("result") != null && "error".equals(map1.get("result"))) {
      returnMap.put("result", "error");
      returnMap.put("errormsg", map1.get("errormsg"));
      return returnMap;
    }
    returnMap.put("info", map1.get("info"));
    sbf.append(map1.get("message") == null ? "" : map1.get("message").toString());

    Map<String, Object> map2 =
        requestBankcard(factor, imageEntityMap.get("bankCardmd5check").toString(), imageEntity4);
    returnMap.put("bank", map2.get("bank"));
    sbf.append(map2.get("message") == null ? "" : map2.get("message").toString());

/*    Map<String, Object> map3 =
        requestPersonPic(factor, imageEntityMap.get("idcardBackmd5check").toString(),
            imageEntityMap.get("idcardPersonPicmd5check").toString(), imageEntity2, imageEntity3);
    returnMap.put("personpicresult", map3.get("personpicresult"));
    sbf.append(map3.get("message") == null ? "" : map3.get("message").toString());*/

    returnMap.put("warning", sbf.toString());
    return returnMap;
  }

  //身份证正反面接口
  private Map<String, Object> requestIdcerdinfo(PersonFactor factor, String idcardmd5check1,
      String idcardmd5check2,
      ImageEntity imageEntity1, ImageEntity imageEntity2) {
    Map<String, Object> returnMap = new HashMap<>();
    String message = "";
    try {
      setAttachValues();

      IdentifyRecord identifyRecord =
          getIdentifyRecord("getIdCardInfo", idcardmd5check1, idcardmd5check2,
              factor.getUserid(), null, null, null);

      BaseResultVo<IdCardEntity> idcardresult = new BaseResultVo<IdCardEntity>();
      if (identifyRecord != null && "success".equals(identifyRecord.getStatus())) {
        idcardresult.setCode(identifyRecord.getCode());
        IdCardEntity entity = new IdCardEntity();
        IdCardEntity.Info info = entity.new Info();
        info.setName(identifyRecord.getName());
        info.setNumber(identifyRecord.getNumber());
        info.setAddress(identifyRecord.getAddress());
        info.setAuthority(identifyRecord.getAuthority());
        info.setSex(identifyRecord.getSex());
        info.setYear(identifyRecord.getYear());
        info.setTimelimit(identifyRecord.getTimelimit());
        entity.setInfo(info);
        idcardresult.setResultData(entity);
        idcardresult.setSuccess(true);
      } else {
        log.info("身份证正反面接口开始----------------------");
        idcardresult = linkFaceOCRService.getIdCardInfo(imageEntity1,
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
        String timebegin = certificatestartdate.substring(0, 4);
        String timeend = certificateexpiredate.substring(0, 4);
        if (StringUtils.isEmpty(info.getName()) || !info.getName().equals(factor.getUsername())) {
          returnMap.put("result", "error");
          returnMap.put("errormsg", "身份证姓名不匹配");
          return returnMap;
        }
        if (StringUtils.isEmpty(info.getNumber()) || !info.getNumber().toUpperCase()
            .equals(factor.getCertificatenumber().toUpperCase())) {
          log.info("照片识别的身份证号码:"
              + info.getNumber()
              + "----------PersonFactor身份证号码:"
              + factor.getCertificatenumber());
          returnMap.put("result", "error");
          returnMap.put("errormsg", "身份证号码不匹配");
          return returnMap;
        }
        if (!factor.getCertificatestartdate().replace("-", "").equals(certificatestartdate)
            || !factor.getCertificateexpiredate().replace("-", "").equals(certificateexpiredate)) {
          log.info("身份证有效期不匹配:info.getTimelimit()-->"
              + info.getTimelimit()
              + ";certificatestartdate:"
              + factor.getCertificatestartdate()
              + ";certificateexpiredate:"
              + factor.getCertificateexpiredate());
          returnMap.put("result", "error");
          returnMap.put("errormsg", "身份证有效期不匹配");
          return returnMap;
        }
        if (DateUtil.toLocalDate(certificateexpiredate, "yyyyMMdd").compareTo(LocalDate.now())
            < 1) {
          log.info("身份证过期");
          returnMap.put("result", "error");
          returnMap.put("errormsg", "身份证过期");
          return returnMap;
        }
        int idcardyears = Integer.parseInt(timeend) - Integer.parseInt(timebegin);
        String birth = info.getYear();
        int age = Integer.parseInt(timebegin) - Integer.parseInt(birth);
        //小于十六周岁的，发给有效期五年的居民身份证；
        if (age < 16 && idcardyears != 5) {
          log.info("age:" + age + "----------idcardyears:" + idcardyears);
          returnMap.put("result", "error");
          returnMap.put("errormsg", "身份证有效期不合法");
          return returnMap;
        }
        //十六周岁至二十五周岁的，发给有效期十年的居民身份证；
        if ((age >= 16 && age <= 25) && idcardyears != 10) {
          log.info("age:" + age + "----------idcardyears:" + idcardyears);
          returnMap.put("result", "error");
          returnMap.put("errormsg", "身份证有效期不合法");
          return returnMap;
        }
        //二十六周岁至四十五周岁的，发给有效期二十年的居民身份证；
        if ((age > 25 && age <= 45) && idcardyears != 20) {
          log.info("age:" + age + "----------idcardyears:" + idcardyears);
          returnMap.put("result", "error");
          returnMap.put("errormsg", "身份证有效期不合法");
          return returnMap;
        }
      } else {
        message = getMessage(idcardresult);
      }
    } catch (Exception e) {
      message = "识别身份证正反面图片接口异常";
      log.info("识别身份证正反面图片接口异常");
      e.printStackTrace();
    }
    returnMap.put("message", message);
    return returnMap;
  }

  private Map<String, Object> requestBankcard(PersonFactor factor, String md5check,
      ImageEntity imageEntity4) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    String message = "";
    try {
      //银行卡
      setAttachValues();
      IdentifyRecord identifyRecord = getIdentifyRecord("getBankCardInfo", md5check, null,
          factor.getUserid(), null, null, null);
      BaseResultVo<BankCardEntity> bankresult = new BaseResultVo<BankCardEntity>();
      if (identifyRecord != null && "success".equals(identifyRecord.getStatus())) {
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
        bankresult = linkFaceOCRService.getBankCardInfo(imageEntity4);
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
      log.info("bankresult:" + bankresult.toString());
      if (bankresult.isSuccess()) {
        returnMap.put("bank", bankresult.getResultData());
      } else {
        message = getMessage(bankresult);
      }
    } catch (Exception e) {
      message = "识别银行卡图片接口异常";
      log.info("识别银行卡图片接口异常");
      e.printStackTrace();
    }
    returnMap.put("message", message);
    return returnMap;
  }

  private Map<String, Object> requestPersonPic(PersonFactor factor, String idcardmd5check1,
      String idcardmd5check2,
      ImageEntity imageEntity2, ImageEntity imageEntity3) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    String message = "";
    try {
      IdentifyRecord identifyRecord =
          getIdentifyRecord("photoMatchIdCard", idcardmd5check1, idcardmd5check2,
              factor.getUserid(), null, null, null);
      BaseResultVo<String> personresult = new BaseResultVo<String>();
      if (identifyRecord != null && "success".equals(identifyRecord.getStatus())) {
        personresult.setCode(identifyRecord.getCode());
        personresult.setResultData(identifyRecord.getSimilarity());
        personresult.setSuccess(true);
      } else {
        //个人照片与身份证对比
        setAttachValues();
        personresult = faceRecognitionService.photoMatchIdCard(imageEntity2,
            imageEntity3);
        log.info("personresult:" + personresult.toString());
        if (personresult.isSuccess()) {
          returnMap.put("personpicresult", personresult.getResultData());
          IdentifyRecord record = new IdentifyRecord();
          record.setCustomerid(factor.getUserid());
          record.setStatus("success");
          record.setCode(personresult.getCode());
          record.setInterfacename("photoMatchIdCard");
          record.setMd5checksum1("idcardmd5check1");
          record.setMd5checksum2("idcardmd5check2");
          record.setCheckorderid(factor.getCheckorderid());
          identifyRecordService.insert(record);
          message = "个人照片与身份证照片相似度：" + personresult.getResultData();
        } else {
          message = getMessage(personresult);
        }
      }
    } catch (Exception e) {
      message = "个人照片与身份证照片比对接口异常";
      log.info("识别银行卡图片接口异常个人照片与身份证照片比对接口异常");
      e.printStackTrace();
    }
    returnMap.put("message", message);
    return returnMap;
  }

  public Map<String, Object> creditPY(PersonFactor personFactor, String name, String number) {
    Map<String, Object> returnMap = new HashMap<>();
    IdentifyRecord creditRecord = getIdentifyRecord("personBasicInfo", null,
        personFactor.getUserid(), null, name, number, null);
    if (creditRecord == null) {
      CommonDto dto = new CommonDto();
      dto = setBasicInfo(dto);
      dto.setQueryName(name);
      dto.setIdType(0);
      dto.setIdCode(number);
      try {
        log.info("鹏元认证接口开始----------------------");
        PersonBaseInfoRespDto response = creditService.personBasicInfo(dto);
        log.info("鹏元认证接口结束----------------------");

        String status = response.getCisReport().getPersonBaseInfo().getVerifyResult();
        IdentifyRecord record = new IdentifyRecord();
        record.setCustomerid(personFactor.getUserid());
        record.setStatus("success");
        record.setCode(status);
        record.setInterfacename("personBasicInfo");
        record.setName(name);
        record.setNumber(number);
        record.setCheckorderid(personFactor.getCheckorderid());
        identifyRecordService.insert(record);
        if ("1".equals(status)) {
          log.info("--姓名：" + name + "--身份证号：" + number + "鹏元征信验证成功");
        } else {
          returnMap.put("result", "error");
          returnMap.put("errormsg", "鹏元征信验证姓名身份证号不通过");
          log.info("--姓名：" + name + "-身份证号：" + number + "鹏元征信验证失败；结果为" + status);
        }
      } catch (Exception e) {
        log.info("--姓名：" + name + "--身份证号：" + number + "鹏元征信接口异常");
        returnMap.put("warning", "调用鹏元接口异常");
        e.printStackTrace();
      }
    } else {
      if ("1".equals(creditRecord.getCode())) {
        log.info("-记录存在-姓名：" + name + "--身份证号：" + number + "鹏元征信验证成功");
      } else {
        returnMap.put("result", "error");
        returnMap.put("errormsg", "鹏元征信验证姓名身份证号不通过");
        log.info("记录存在，姓名：" + name + "-身份证号：" + number + "鹏元征信验证失败:" + creditRecord.getStatus());
      }
    }
    return returnMap;
  }

  @Override public Map<String, Object> creditPhoneCheck(PersonFactor personFactor) {
    Map<String, Object> returnMap = new HashMap<>();
    IdentifyRecord creditRecord = getIdentifyRecord("phoneNoCheck", null,
        null, personFactor.getUserid(), personFactor.getUsername(), null,
        personFactor.getMobilephone());
    if (creditRecord == null) {
      PhoneNoCheckDto dto = new PhoneNoCheckDto();
      dto.setSysNo(busiNo);
      dto.setTransCode(TransCode.COMMON_MOBILE_CHECK.getTransCode());
      dto.setOrgNo(orgNo);
      dto.setSignType(1);
      String singMessage = dto.sign(pyKey);
      dto.setSignMsg(singMessage);
      dto.setQueryName(personFactor.getUsername());
      dto.setPhoneNo(personFactor.getMobilephone());
      try {
        PhoneNoCheckRespDto response = creditService.phoneNoCheck(dto);
        if (response.getCisReport().getMobileCheckInfo().getItem() != null) {
          String namecheck =
              response.getCisReport().getMobileCheckInfo().getItem().getNameCheckResult();
          String phonecheck =
              response.getCisReport().getMobileCheckInfo().getItem().getPhoneCheckResult();
          String area = response.getCisReport().getMobileCheckInfo().getItem().getAreaInfo();
          String operator = response.getCisReport().getMobileCheckInfo().getItem().getOperator();
          IdentifyRecord record = new IdentifyRecord();
          record.setCustomerid(personFactor.getUserid());
          record.setCheckorderid(personFactor.getCheckorderid());
          record.setInterfacename("phoneNoCheck");
          record.setStatus("success");
          record.setCode(response.getCisReport().getMobileCheckInfo().getTreatResult());
          record.setName(personFactor.getUsername());
          record.setPhone(personFactor.getMobilephone());
          record.setNamecheck(namecheck);
          record.setPhonecheck(phonecheck);
          record.setPhonearea(area);
          record.setPhoneoperator(operator);
          identifyRecordService.insert(record);
          if ("一致".equals(namecheck) && "一致".equals(phonecheck)) {
            log.info("姓名与手机号码，鹏元接口的校验结果一致");
          } else {
            returnMap.put("warning", "姓名与手机号码，鹏元接口的校验结果不一致");
            log.info("姓名与手机号码，鹏元接口的校验结果不一致");
          }
        } else {
          returnMap.put("warning", "未查到手机号码与姓名的记录");
        }
      } catch (Exception e) {
        returnMap.put("warning", "调用鹏元接口验证姓名与手机号码异常");
        e.printStackTrace();
      }
    } else {
      if ("一致".equals(creditRecord.getNamecheck()) && "一致".equals(creditRecord.getPhonecheck())) {
        log.info("记录存在，姓名与手机号码，鹏元接口的校验结果一致");
      } else {
        returnMap.put("warning", "姓名与手机号码，鹏元接口的校验结果不一致");
        log.info("记录存在，姓名与手机号码，鹏元接口的校验结果不一致");
      }
    }
    return returnMap;
  }

  private IdentifyRecord getIdentifyRecord(String interfacename, String md5check1, String md5check2,
      String customerid,
      String name, String number, String phone) {
    IdentifyRecord identifyRecord = new IdentifyRecord();
    identifyRecord.setInterfacename(interfacename);
    identifyRecord.setCustomerid(customerid);
    if ("personBasicInfo".equals(interfacename)) {
      identifyRecord.setName(name);
      identifyRecord.setNumber(number);
    } else if ("getBankCardInfo".equals(interfacename)) {
      identifyRecord.setMd5checksum1(md5check1);
    } else if ("phoneNoCheck".equals(interfacename)) {
      identifyRecord.setName(name);
      identifyRecord.setPhone(phone);
    } else {
      identifyRecord.setMd5checksum1(md5check1);
      identifyRecord.setMd5checksum2(md5check2);
    }
    IdentifyRecord returnrecord = identifyRecordService.queryOne(identifyRecord);
    return returnrecord;
  }

  private static Map<String, String> getResponseCode() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("ENCODING_ERROR", "参数非UTF-8编码");
    map.put("DOWNLOAD_TIMEOUT", "网络地址图片获取超时,详情见字段 image");
    map.put("DOWNLOAD_ERROR", "网络地址图片获取失败，详情见字段 image");
    map.put("IMAGE_FILE_SIZE_TOO_BIG", "图片体积过大，详情见字段 image");
    map.put("IMAGE_ID_NOT_EXIST", "图片不存在，详情见字段 image");
    map.put("NO_FACE_DETECTED", "图片未检测出人脸 ，详情见字段 image");
    map.put("CORRUPT_IMAGE", "不是图片文件或已经损坏，详情见字段 image");
    map.put("INVALID_IMAGE_FORMAT_OR_SIZE", "图片大小或格式不符合要求，详情见字段 image");
    map.put("INVALID_ARGUMENT", "请求参数错误，具体原因见 reason 字段内容");
    map.put("UNAUTHORIZED", "账号或密钥错误");
    map.put("KEY_EXPIRED", "账号过期，具体情况见 reason 字段内容");
    map.put("RATE_LIMIT_EXCEEDED", "调用频率超出限额");
    map.put("NO_PERMISSION", "无调用权限");
    map.put("NOT_FOUND", "请求路径错误");
    map.put("INTERNAL_ERROR", "服务器内部错误");
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
        JSONObject jsonObject = JSONObject.fromObject(clas.getErrorMsg());
        mes =
            getResponseCode().get(jsonObject.get("status")) == null ? clas.getErrorMsg()
                : getResponseCode().get(jsonObject.get("status"));
      } catch (Exception e) {
        mes = clas.getErrorMsg();
      }
    } else {
      mes = clas.getErrorMsg();
    }
    return mes;
  }
}
