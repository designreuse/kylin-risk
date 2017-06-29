package com.rkylin.risk.core.exception.model;

import java.io.Serializable;

/**
 * @company: Yeepay
 * @author: tongzhuyu
 * @since: 2015/8/31
 */
public class ErrorDesc implements Serializable {

  private int code;
  private String message;

  public ErrorDesc(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
