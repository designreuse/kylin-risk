package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.operation.api.MerchantCheckApi;
import com.rkylin.risk.service.biz.MerchantBiz;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by lina on 2016-8-16.
 */
@Component("merchantCheckApi")
@Slf4j
public class MerchantCheckApiImpl implements MerchantCheckApi {

  private ObjectMapper objectMapper = new ObjectMapper();
  @Resource
  private MerchantBiz merchantBiz;

  @Override public String checkMerchantMsg(String checkorderid) {
    try {
      log.info("【dubbo服务准入审核结果查询】输入参数：checkorderid="
          + checkorderid);
      if (StringUtils.isEmpty(checkorderid)) {
        log.info("【准入审核结果查询失败】-查询参数为空");
        return getResultStr("1", "查询参数为空");
      }
      String dataStr = merchantBiz.queryMerchantmsgHandle(checkorderid);
      String resultStr = getResultStr("0", dataStr);
      log.info("【准入审核结果查询成功】返回信息:" + resultStr);
      return resultStr;
    } catch (Exception e) {
      log.error("【准入审核结果查询失败】-风控系统异常", e);
      return "{\"resultCode\":\"99\",\"resultMsg\":\"风控处理异常\"}";
    }
  }

  @Override public String calcMerchantMsg(String checkorderid) {
    try {
      log.info("【dubbo服务预算查询】输入参数：checkorderid="
          + checkorderid);
      if (StringUtils.isEmpty(checkorderid)) {
        log.info("【预算查询失败】-查询参数为空");
        return "";
      }
      String resultStr = merchantBiz.calcMerchantMsgHandle(checkorderid);
      log.info("【预算查询成功】返回信息:" + resultStr);
      return resultStr;
    } catch (Exception e) {
      log.info("【预算查询失败】-风控系统异常", e);
      return "";
    }
  }

  private String getResultStr(String code, String msg) throws JsonProcessingException {
    Map map = new HashMap();
    map.put("resultCode", code);
    map.put("resultMsg", msg);
    String resultStr = objectMapper.writeValueAsString(map);
    return resultStr;
  }
}
