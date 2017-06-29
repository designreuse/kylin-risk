package com.rkylin.risk.service.biz.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.gateway.dto.CommonDto;
import com.rkylin.gateway.dto.personBaseInfo.PersonBaseInfoRespDto;
import com.rkylin.gateway.dto.phoneNoCheck.PhoneNoCheckDto;
import com.rkylin.gateway.dto.phoneNoCheck.PhoneNoCheckRespDto;
import com.rkylin.gateway.enumtype.TransCode;
import com.rkylin.gateway.service.CreditService;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.entity.IdentifyRecord;
import com.rkylin.risk.core.enumtype.CreditProductType;
import com.rkylin.risk.core.service.CreditResultService;
import com.rkylin.risk.core.service.IdentifyRecordService;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.PengYuanCreditBiz;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 鹏元征信
 *
 * @author qiuxian
 * @create 2016-08-29 11:38
 **/
@Component("pengYuanCreditBiz")
@Slf4j
public class PengYuanCreditBizImpl implements PengYuanCreditBiz {

  @Resource
  private CreditService creditService;
  @Resource
  private IdentifyRecordService identifyRecordService;
  @Resource
  private CreditResultService creditResultService;

  @Value("${credit.py.key}")
  private String pyKey;
  @Value("${credit.py.busiNo}")
  private String busiNo;
  @Value("${credit.py.orgNo}")
  private String orgNo;

  @Resource
  private ObjectMapper jsonMapper;

  private CommonDto setBasicInfo(CommonDto dto) {
    dto.setSysNo(busiNo);
    dto.setTransCode(TransCode.PERSON_BASIC_INFO.getTransCode());
    dto.setOrgNo(orgNo);
    dto.setSignType(1);
    String singMessage = dto.sign(pyKey);
    dto.setSignMsg(singMessage);
    return dto;
  }

  public Map<String, String> creditPY(String name, String number) {
    Map<String, String> returnMap = new HashMap<>();
    CreditResult creditRecord = creditResultService.queryCommonCreditResult(number,
        CreditProductType.PY, "19117");
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
        if (response.getCisReport() == null) {
          log.info("鹏元CisReport()为空");
          returnMap.put("warning", "调用鹏元接口异常;");
          return returnMap;
        }
        if (response.getCisReport().getPersonBaseInfo() == null) {
          log.info("PersonBaseInfo()为空");
          returnMap.put("warning", "调用鹏元接口异常;");
          return returnMap;
        }
        String status = response.getCisReport().getPersonBaseInfo().getVerifyResult();
        CreditResult externalCreditResult = new CreditResult();
        if ("100000".equals(response.getReturnCode())) {
          externalCreditResult.setRiskCode("10000");
        } else if ("110001".equals(response.getReturnCode())) {
          externalCreditResult.setRiskCode("11000");
        } else if ("120001".equals(response.getReturnCode())) {
          externalCreditResult.setRiskCode("11001");
        } else {
          externalCreditResult.setRiskCode("99999");
        }
        externalCreditResult.setUserName(name);
        externalCreditResult.setIdNumber(number);
        externalCreditResult.setCreditProduct(CreditProductType.PY.name());
        ObjectMapper objectMapper = new ObjectMapper();
        String aaa = objectMapper.writeValueAsString(response.getCisReport().getPersonBaseInfo());
        externalCreditResult.setResult(aaa);
        externalCreditResult.setCreditCode(response.getReturnCode());
        externalCreditResult.setRequestTime(new DateTime());
        externalCreditResult.setResponseId(response.getCisReport().getReportID());
        creditResultService.insert(externalCreditResult, new String[]{"19117"});
        if ("1".equals(status)) {
          log.info("--姓名：" + name + "--身份证号：" + number + "鹏元征信验证成功");
        } else {
          returnMap.put("result", "error");
          returnMap.put("errormsg", "鹏元征信验证姓名身份证号不通过;");
          log.info("--姓名：" + name + "-身份证号：" + number + "鹏元征信验证失败；结果为" + status);
        }
      } catch (Exception e) {
        log.info("--姓名：" + name + "--身份证号：" + number + "鹏元征信接口异常");
        returnMap.put("warning", "调用鹏元接口异常;");
        log.info("调用鹏元接口异常,异常信息{}", e);
      }
    } else {
      try {
        JsonNode jsonNode = jsonMapper.readTree(creditRecord.getResult());
        if ("1".equals(jsonNode.path("verifyResult").asText())) {
          log.info("-记录存在-姓名：" + name + "--身份证号：" + number + "鹏元征信验证成功");
        } else {
          returnMap.put("result", "error");
          returnMap.put("errormsg", "鹏元征信验证姓名身份证号不通过;");
          log.info(
              "记录存在，姓名：" + name + "-身份证号：" + number + "鹏元征信验证失败,结果:" + jsonNode.path("treatResult")
                  .asText());
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return returnMap;
  }

  @Override public Map<String, Object> creditPhoneCheck(PersonFactor personFactor) {
    Map<String, Object> returnMap = new HashMap<>();
    IdentifyRecord creditRecord = getIdentifyRecord("phoneNoCheck", null,
        null, personFactor.getUserid());
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
}
