package com.rkylin.risk.core.exception;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/25 version: 1.0
 */
public class RiskConvertException extends RuntimeException {
  public RiskConvertException() {
    super();
  }

  public RiskConvertException(String message) {
    super(message);
  }

  public RiskConvertException(String message, Throwable cause) {
    super(message, cause);
  }

  public RiskConvertException(Throwable cause) {
    super(cause);
  }
}
