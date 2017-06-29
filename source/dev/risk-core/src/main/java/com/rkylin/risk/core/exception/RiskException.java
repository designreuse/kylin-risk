package com.rkylin.risk.core.exception;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/6 version: 1.0
 */
public class RiskException extends RuntimeException {
  protected RiskException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public RiskException() {
    super();
  }

  public RiskException(String message) {
    super(message);
  }

  public RiskException(String message, Throwable cause) {
    super(message, cause);
  }

  public RiskException(Throwable cause) {
    super(cause);
  }
}
