package com.rkylin.risk.service.api.impl;

import com.rkylin.risk.operation.api.OperationApi;
import com.rkylin.risk.operation.bean.CustomerMsg;
import com.rkylin.risk.operation.bean.ResultInfo;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.biz.CustomerBiz;
import com.rkylin.risk.service.biz.CustomerCalBiz;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by lina on 2016-4-5.
 */
@Component("operationApi")
@Slf4j
public class OperationApiImpl implements OperationApi {
  @Resource
  CustomerBiz customerBiz;
  @Resource
  CustomerCalBiz customerCalBiz;

  /**
   * 客户评分接口处理
   */
  @Override
  public ResultInfo customerinfo(CustomerMsg customerMsg, String hmac) {
    log.info("request data:{},hmac:{}", customerMsg, hmac);
    ResultInfo resultInfo = null;
    try {
      //客户评分接口参数校验
      resultInfo = customerinfoValidate(customerMsg, hmac);
      if (resultInfo == null) {
        //验证成功
        //客户评分处理
        resultInfo = customerBiz.customerInfoHandle(customerMsg);
        log.info("客户：" + customerMsg.getCustomername() + ", 分数获取成功！");
      } else {
        //验证失败
        log.info("客户" + resultInfo.getCustomerName() + ", 分数获取失败," + resultInfo.getResultMsg());
      }
    } catch (Exception e) {
      log.info("客户：" + customerMsg.getCustomername() + ",分数获取失败，风控系统处理异常！", e);
      resultInfo = newResultInfo(customerMsg, "风控系统处理异常");
    }
    return resultInfo;
  }

  private ResultInfo newResultInfo(String message) {
    ResultInfo resultInfo = new ResultInfo();
    resultInfo.setResultCode("99");
    resultInfo.setResultMsg(message);
    return resultInfo;
  }

  private ResultInfo newResultInfo(CustomerMsg customerMsg, String message) {
    ResultInfo resultInfo = new ResultInfo();
    resultInfo.setResultCode("99");
    resultInfo.setResultMsg(message);
    resultInfo.setCustomerName(customerMsg.getCustomername());
    resultInfo.setCertificateId(customerMsg.getCertificateid());
    return resultInfo;
  }

  /**
   * 客户评分接口参数校验
   *
   * @return 验证通过返回null, 验证异常返回 99-resultInfo
   */
  public ResultInfo customerinfoValidate(CustomerMsg customerMsg, String hmac) {
    //为空判断
    if (customerMsg == null) {
      return newResultInfo("客户信息为空");
    }
    if (isBlank(hmac)) {
      return newResultInfo("签名信息为空!");
    }
    if (isEmpty(customerMsg.getCustomername()) || isEmpty(customerMsg.getCertificateid())) {
      return newResultInfo(customerMsg, "订单中有一个以上字段为空,请检查订单数据!");
    }

    //校验签名
    String hmacString = new StringBuilder(customerMsg.getCustomerid())
        .append(customerMsg.getCustomername()).append(customerMsg.getCertificateid())
        .append(ApiServiceConstants.OPER_API_HMAC).toString();
    String md5HmacString = DigestUtils.md5Hex(hmacString);
    if (!hmac.equals(md5HmacString)) {
      return newResultInfo(customerMsg, "签名校验不一致!");
    }
    return null;
  }
}
