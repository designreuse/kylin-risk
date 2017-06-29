package com.rkylin.risk.commons.enumtype;

/**
 * Created by tomalloc on 16-8-2.
 */
public enum CreditProductType {

  /**
   * 百融
   */
  BAIRONG("百融"),
  /**
   * 鹏元
   */
  PY("鹏元"),
  /**
   * 银联智策
   */
  UNIONPAY("银联智策");
  private String title;

  CreditProductType(String title) {
    this.title = title;
  }

  public String title() {
    return title;
  }
}
