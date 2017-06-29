package com.rkylin.risk.service.enumtype;

/**
 * Created by cuixiaofang on 2016-3-29.
 */
public enum OrderType {
  B("B", "基础订单类型"),
  B1("B1", "基础充值订单"),
  B11("B11", "网关充值wap支付"),
  B12("B12", "移动支付快捷SDK支付"),
  B13("B13", "移动支付快捷wap支付"),
  B14("B14", "移动支付认证SDK支付"),
  B15("B15", "移动支付认证wap支付"),
  B2("B2", "基础提现订单");

  private String value;

  private String description;

  OrderType(String value, String description) {
    this.value = value;
    this.description = description;
  }

  public String getValue() {
    return value;
  }

  public String getDescription() {
    return description;
  }
}
