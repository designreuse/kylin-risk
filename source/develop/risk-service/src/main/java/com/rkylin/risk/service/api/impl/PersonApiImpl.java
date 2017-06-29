package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.rkylin.risk.operation.api.PersonApi;
import com.rkylin.risk.operation.bean.PersonMsg;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.PersonBiz;
import java.io.IOException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by cuixiaofang on 2016-7-28.
 */
@Component("personApi")
@Slf4j
public class PersonApiImpl implements PersonApi {
  @Resource
  private PersonBiz personBiz;

  private ObjectMapper objectMapper = new ObjectMapper();


  @Override
  public PersonMsg personmsg(String data) {
    long staTimeMillis = DateTime.now().getMillis();
    log.info("request data:{}", data);
    PersonFactor personFactor = jsonToPerson(data);

    if (personFactor == null) {
      return exceptionPersonMsg("【风控系统】个人数据为空! ");
    }

    if (isBlank(personFactor.getHmac())) {
      return exceptionPersonMsg("【风控系统】签名字符串为空!");
    }

    String hmacString = new StringBuilder(personFactor.getUserid())
        .append(personFactor.getConstid())
        .append(personFactor.getUsername())
        .append(personFactor.getClassname())
        .append(ApiServiceConstants.ORDER_API_HMAC)
        .toString();

    if (!personFactor.getHmac().equals(DigestUtils.md5Hex(hmacString))) {
      return exceptionPersonMsg("【风控系统】签名校验失败!");
    }

    Boolean isOff;
    try {
      isOff = personBiz.personmsg(personFactor);
      if (isOff) {
        return newSuccessResultInfo("【风控系统】个人信息-->校验通过");
      } else {
        return newFailResultInfo("【风控系统】校验该个人数据为高风险");
      }
    } catch (Exception e) {
      log.info("系统错误，错误消息:【{}】\n 错误完整信息:{}", e.getMessage(), Throwables.getStackTraceAsString(e));
      return exceptionPersonMsg("系统产生错误，请联系管理员");
    } finally {
      log.info("风控个人信息录入流程checkorderid：{}，-------------------》{}", personFactor.getCheckorderid(),
          DateTime.now().getMillis() - staTimeMillis);
    }
  }

  private PersonMsg newSuccessResultInfo(String message) {
    PersonMsg personMsg = new PersonMsg();
    personMsg.setResultCode("0");
    personMsg.setResultMsg(message);
    return personMsg;
  }

  private PersonMsg newFailResultInfo(String message) {
    PersonMsg personMsg = new PersonMsg();
    personMsg.setResultCode("1");
    personMsg.setResultMsg(message);
    return personMsg;
  }

  private PersonMsg exceptionPersonMsg(String message) {
    PersonMsg personMsg = new PersonMsg();
    personMsg.setResultCode("99");
    personMsg.setResultMsg(message);
    return personMsg;
  }

  /**
   * 个人
   */
  private PersonFactor jsonToPerson(String jsonStr) {
    PersonFactor personFactor = null;
    if (isNotEmpty(jsonStr)) {
      try {
        personFactor = objectMapper.readValue(jsonStr, PersonFactor.class);
      } catch (JsonParseException e) {
        e.printStackTrace();
      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    log.info("【风控系统】个人数据:{}", personFactor);
    return personFactor;
  }
}
