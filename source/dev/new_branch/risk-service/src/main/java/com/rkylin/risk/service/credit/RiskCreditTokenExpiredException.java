package com.rkylin.risk.service.credit;

/**
 * Created by tomalloc on 16-12-14.
 */
public class RiskCreditTokenExpiredException extends RuntimeException {

  public RiskCreditTokenExpiredException(String s) {
    super(s);
  }
}
