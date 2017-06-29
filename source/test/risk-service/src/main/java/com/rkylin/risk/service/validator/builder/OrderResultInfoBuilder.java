package com.rkylin.risk.service.validator.builder;

import com.rkylin.risk.order.bean.ResultInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tomalloc on 16-4-14.
 */
@Slf4j
public class OrderResultInfoBuilder implements Builder<ResultInfo> {
  /**
   * 内部订单号
   */
  private String orderId;
  /**
   * 返回码
   */
  private String resultCode;
  /**
   * 返回信息
   */
  private String resultMsg;

  private OrderResultInfoBuilder() {
  }

  public static OrderResultInfoBuilder builder() {
    return new OrderResultInfoBuilder();
  }

  public OrderResultInfoBuilder orderId(String orderId) {
    this.orderId = orderId;
    return this;
  }

  public OrderResultInfoBuilder resultCode(String resultCode) {
    this.resultCode = resultCode;
    return this;
  }

  public OrderResultInfoBuilder resultMsg(String resultMsg) {
    this.resultMsg = resultMsg;
    return this;
  }

  @Override
  public ResultInfo build() {
    ResultInfo resultInfo = new ResultInfo();
    resultInfo.setOrderId(orderId);
    resultInfo.setResultCode(resultCode);
    resultInfo.setResultMsg(resultMsg);
    log.info("【dubbo服务】风控系统返回订单系统参数:{}", resultInfo);
    return resultInfo;
  }
}
