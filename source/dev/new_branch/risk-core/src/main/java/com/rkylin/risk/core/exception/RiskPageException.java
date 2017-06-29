package com.rkylin.risk.core.exception;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/6 version: 1.0
 */
public class RiskPageException extends RuntimeException {
  protected RiskPageException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public RiskPageException() {
    super();
  }

  public RiskPageException(String message) {
    super(message);
  }

  public RiskPageException(String message, Throwable cause) {
    super(message, cause);
  }

  public RiskPageException(Throwable cause) {
    super(cause);
  }
}
