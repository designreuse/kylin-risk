package com.rkylin.risk.credit.exception;

/**
 * Created by tomalloc on 16-7-29.
 */
public class RiskCreditTokenExpireException extends RuntimeException {

  public RiskCreditTokenExpireException() {
    super();
  }

  public RiskCreditTokenExpireException(String message) {
    super(message);
  }

  public RiskCreditTokenExpireException(String message, Throwable cause) {
    super(message, cause);
  }

  public RiskCreditTokenExpireException(Throwable cause) {
    super(cause);
  }

  protected RiskCreditTokenExpireException(String message, Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
