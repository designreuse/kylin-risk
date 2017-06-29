package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.rkylin.risk.core.exception.RiskException;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.mitou.api.MitouQueryStatusApi;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.api.ApiMitouConstants;
import com.rkylin.risk.service.bean.MitouGetUserInfoRequestParam;
import com.rkylin.risk.service.bean.MitouGetUserInfoResponseParam;
import com.rkylin.risk.service.bean.MitouResponseParam;
import com.rkylin.risk.service.biz.MitouBiz;
import java.io.IOException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.service.api.ApiMitouConstants.ACCEPT;
import static com.rkylin.risk.service.api.ApiMitouConstants.COMMIT;
import static com.rkylin.risk.service.api.ApiMitouConstants.GRABBING;

/**
 * Created by chenfumin on 2016/12/20.
 */
@Slf4j
@Component("mitouQueryStatusApi")
public class MitouQueryStatusApiImpl implements MitouQueryStatusApi {

  @Resource
  private MitouBiz mitouBiz;

  @Resource
  private OperateFlowService operateFlowService;

  @Value("${mitou.platid}")
  private int platid;

  @Value("${mitou.testMode}")
  private String testMode;

  @Resource
  private ObjectMapper objectMapper;

  /**
   * 查询授权状态
   * @return json
   */
  public String queryAuthStatus(String data) {
    log.info("【风控系统-米投服务】------------开始获取米投的用户信息-----------");
    log.info("【风控系统-米投服务】------------接受的JSON数据:{}-----------", data);

    if (true) {
      log.info("------------测试模式，直接返回授权成功-----------");
      return "{\"mobileAuthStatus\":\"true\",\"onlineRetailerStatus\":\"true\"}";
    }

    if (StringUtils.isBlank(data)) {
      return "{\"status\":\"error\", \"msg\":\"传入的数据不能为空\"}";
    }

    String platuserid = null;
    String constid = null;
    String orderid = null;

    try {
      JsonNode jsonNode = objectMapper.readTree(data);

      if (!jsonNode.hasNonNull("userid")) {
        return "{\"status\":\"error\", \"msg\":\"请填写用户UID\"}";
      }

      platuserid = jsonNode.get("userid").asText();

      // 可选参数: 机构号
      if (jsonNode.hasNonNull("constid")) {
        constid = jsonNode.get("constid").asText();
        log.info("【风控系统-米投服务】-------- 需要查询的机构号 : {} -----------", constid);
      }

      // 可选参数: 订单号
      if (jsonNode.hasNonNull("orderNo")) {
        orderid = jsonNode.get("orderNo").asText();
        log.info("【风控系统-米投服务】-------- 需要查询的订单号 : {} -----------", orderid);
      }

    } catch (IOException e) {
      log.info("【风控系统-米投服务】------------解析json出错, 解析的json字符为:{}-----------", data);
      throw new RiskException("查询授权状态,解析入参出现异常");
    }
    log.info("【风控系统-米投服务】------------查询用户平台ID: {}, 机构号: {} ---------", platuserid, constid);
    MitouGetUserInfoRequestParam param = new MitouGetUserInfoRequestParam();
    param.setPlatuserid(platuserid);

    // 可选参数
    if (StringUtils.isNotBlank(orderid) && !"null".equals(orderid)) {
      param.setOrderid(orderid);
    }

    if (StringUtils.isNotBlank(constid) && !"null".equals(orderid)) {
      if (RuleConstants.XINGREN_CHANNEL_ID.equals(constid)) {
        param.setDataSource(ApiMitouConstants.MITOU_DATA_SOURCE_APP);
      } else {
        param.setDataSource(ApiMitouConstants.MITOU_DATA_SOURCE_H5);
      }
    }
    log.info("【风控系统-米投服务】------------准备调用米投服务，传入参数平台ID:{}, 用户ID:{}---------", platid, platuserid);
    MitouResponseParam<MitouGetUserInfoResponseParam> userinfo = null;

    try {
      userinfo = mitouBiz.requestUserinfo(platid, param);
    } catch (Exception e) {
      log.info("【风控系统-米投服务】------------调用米投服务出现异常----------------");
      log.info("异常信息:{}", Throwables.getStackTraceAsString(e));
      return "{\"status\":\"error\", \"msg\":\"该用户未注册\"}";
    }
    log.info("【风控系统-米投服务】------------完成米投服务调用----------------");

    MitouGetUserInfoResponseParam responseParam = userinfo.getData();


    return validAuthStatus(responseParam);
  }

  private String validAuthStatus(MitouGetUserInfoResponseParam responseParam) {
    StringBuilder sb = new StringBuilder();
    String mobileAuthStatus = responseParam.getMobileAuthStatus();
    String onlineRetailerStatus = responseParam.getOnlineRetailerStatus();
    log.info("【风控系统-米投服务】------------手机授权状态:{}, 电商授权状态:{}----------------", mobileAuthStatus, onlineRetailerStatus);
    if (GRABBING.equals(mobileAuthStatus)
          || COMMIT.equals(mobileAuthStatus)
          || ACCEPT.equals(
          mobileAuthStatus)) {
        sb.append("{\"mobileAuthStatus\":\"true\",");
      } else {
        sb.append("{\"mobileAuthStatus\":\"false\",");
      }
      if (GRABBING.equals(onlineRetailerStatus)
          || COMMIT.equals(onlineRetailerStatus)
          || ACCEPT.equals(
          onlineRetailerStatus)) {
        sb.append("\"onlineRetailerStatus\":\"true\"}");
      } else {
        sb.append("\"onlineRetailerStatus\":\"false\"}");
      }
    return sb.toString();
  }
}
