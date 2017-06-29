package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.operation.api.ReptileApi;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.biz.ReptileBiz;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by lina on 2016-8-10.
 */
@Component("reptileApi")
@Slf4j
public class ReptileApiImpl implements ReptileApi {
  private ObjectMapper mapper = new ObjectMapper();
  @Resource
  private ReptileBiz reptileBiz;

  @Override public String reptileInfo(String checkorderId, String querytype, String hmac) {
    log.info("request checkorderId:{},querytype:{},hmac:{}", checkorderId, querytype, hmac);
    String jsonStr = "";
    try {
      Map returnMap = new HashMap();
      returnMap.put("checkorderId", checkorderId);
      returnMap.put("resultCode", "1");
      jsonStr = mapper.writeValueAsString(returnMap);
      if (paramValidate(checkorderId, querytype, hmac) && hmacValidate(checkorderId, hmac)) {
        Map resultMap = reptileBiz.pullVerifyResult(checkorderId, querytype);
        resultMap.put("checkorderId", checkorderId);
        resultMap.put("resultCode", "0");
        jsonStr = mapper.writeValueAsString(resultMap);
      }
    } catch (JsonProcessingException e) {
      log.info("【获取爬虫列表失败】-风控处理异常", e);
    }
    return jsonStr;
  }

  @Override public List<String> reptileDetail(String checkorderId, String querytype, String type,
      String hmac) {
    log.info("request checkorderId:{},querytype:{},hmac:{}", checkorderId, querytype, hmac);
    List<String> list = null;
    try {
      if (paramValidate(checkorderId, type, hmac) && hmacValidate(checkorderId, hmac)) {
        list = reptileBiz.pullVerifyDetail(checkorderId, querytype, Integer.parseInt(type));
      }
    } catch (Exception e) {
      log.info("【获取爬虫详情失败】-风控处理异常", e);
    }
    return list;
  }

  /*
  参数必填校验
   */
  private boolean paramValidate(String v1, String v2, String v3) {
    log.info(
        "【dubbo服务-获取爬虫详情】输入参数为：checkorderId=" + v1 + ",type=" + v2 + "hmac=" + v3);

    return StringUtils.isNotEmpty(v1) && StringUtils.isNotEmpty(v2) && StringUtils.isNotEmpty(v3);
  }

  /*
  签名校验
   */
  private boolean hmacValidate(String v1, String hmac) {
    String hmacString = new StringBuilder(v1).append(ApiServiceConstants.OPER_API_HMAC).toString();
    String md5HmacString = DigestUtils.md5Hex(hmacString);
    return hmac.equals(md5HmacString);
  }
}
