package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.order.api.MerchantApi;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.bean.MerchantFactor;
import com.rkylin.risk.service.biz.MerchantBiz;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by lina on 2016-8-15.
 */
@Slf4j
@Component("merchantApi")
public class MerchantApiImpl implements MerchantApi, InitializingBean, ApplicationContextAware {
  @Resource
  private MerchantBiz merchantBiz;
  @Setter
  private Validator validator;

  private ApplicationContext applicationContext;

  @Resource
  private ObjectMapper jsonMapper;


  @Override public String merchantmsg(String msg) {
    long staTimeMillis = DateTime.now().getMillis();
    log.info("request data:{}", msg);
    MerchantFactor merchantFactor = jsonToMerchant(msg);
    try {

      String errStr = paramValidate(merchantFactor);
      if (StringUtils.isNotEmpty(errStr)) {
        log.info("【机构数据录入失败】-" + errStr);
        return getResultStr("1", errStr);
      }
      if (!hmacValidate(merchantFactor)) {
        log.info("【机构数据录入失败】-签名验证失败");
        return getResultStr("1", "签名验证失败");
      }
      String dataStr = merchantBiz.newMerchantmsgHandle(merchantFactor);
      String resultStr = getResultStr("0", dataStr);
      log.info("【机构数据录入成功】返回信息:" + resultStr);
      return resultStr;
    } catch (Exception e) {
      log.info("【机构数据录入失败】风控处理异常, 异常信息:{}", e);
      return "{\"resultCode\":\"99\",\"resultMsg\":\"风控处理异常\"}";
    } finally {
      log.info("风控机构信息录入流程checkorderid：{}，-------------------》{}", merchantFactor.getCheckorderid(),
          DateTime.now().getMillis() - staTimeMillis);
    }
  }

  /*
   签名验证
   */
  private boolean hmacValidate(MerchantFactor merchantFactor) {
    if (isBlank(merchantFactor.getHmac())) {
      return false;
    }
    String hmacString = DigestUtils.md5Hex(
        new StringBuilder(merchantFactor.getUserid()).append(merchantFactor.getConstid())
            .append(merchantFactor.getCompanyname())
            .append(merchantFactor.getCorporatename())
            .append(ApiServiceConstants.ORDER_API_HMAC)
            .toString());
    log.info("【机构数据录入】风控系统加密密钥:" + hmacString);

    if (merchantFactor.getHmac().equals(hmacString)) {
      return true;
    }
    return false;
  }

  /*
  机构信息合法性验证
   */
  private String paramValidate(MerchantFactor merchantFactor) {
    if (merchantFactor == null) {
      return "机构信息为空";
    } else {
      StringBuilder errStr = new StringBuilder ();
      Set<ConstraintViolation<MerchantFactor>> constraintViolations =
          validator.validate(merchantFactor);
      if (!constraintViolations.isEmpty()) {
        Iterator<ConstraintViolation<MerchantFactor>> iterator = constraintViolations.iterator();
        errStr.append(iterator.next().getMessage());
        while (iterator.hasNext()) {
          errStr.append(",");
          errStr.append(iterator.next().getMessage());
        }
      }
      return errStr.toString();
    }
  }

  /*
  拼接返回参数
   */
  private String getResultStr(String code, String msg) throws JsonProcessingException {
    Map map = new HashMap();
    map.put("resultCode", code);
    map.put("resultMsg", msg);
    return jsonMapper.writeValueAsString(map);
  }

  /**
   * 机构
   */
  private MerchantFactor jsonToMerchant(String jsonStr) {
    MerchantFactor merchantFactor = null;
    if (isNotEmpty(jsonStr)) {
      try {
        merchantFactor = jsonMapper.readValue(jsonStr, MerchantFactor.class);
      } catch (JsonParseException e) {
        log.info("【机构信息录入】机构json串解析异常,异常信息:{}", e);
      } catch (JsonMappingException e) {
        log.info("【机构信息录入】机构json串映射异常,异常信息:{}", e);
      } catch (IOException e) {
        log.info("【机构信息录入】机构json串异常,异常信息:{}", e);
      }
    }
    log.info("【机构数据录入】机构数据:{}", merchantFactor);
    return merchantFactor;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override public void afterPropertiesSet() throws Exception {
    if (validator == null) {
      try {
        validator = applicationContext.getBean("validator", Validator.class);
      } catch (Exception e) {
        log.info(e.getMessage(), e);
      }
    }

    if (validator == null) {
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      validator = factory.getValidator();
    }
  }
}
