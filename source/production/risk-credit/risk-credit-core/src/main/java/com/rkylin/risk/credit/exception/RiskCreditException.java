package com.rkylin.risk.credit.exception;

/**
 * Created by tomalloc on 16-7-29.
 */
public class RiskCreditException extends RuntimeException {
  public RiskCreditException() {
    super();
  }

  public RiskCreditException(String message) {
    super(message);
  }

  public RiskCreditException(String message, Throwable cause) {
    super(message, cause);
  }

  public RiskCreditException(Throwable cause) {
    super(cause);
  }

  protected RiskCreditException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
