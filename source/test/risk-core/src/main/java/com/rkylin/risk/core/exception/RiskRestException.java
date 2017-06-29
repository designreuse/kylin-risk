package com.rkylin.risk.core.exception;

import com.rkylin.risk.core.exception.model.ErrorDesc;

/**
 * @company: rkylin
 * @author: tongzhuyu version: 1.0
 */
public class RiskRestException extends RiskException {
  private final ErrorDesc errorDesc;

  public RiskRestException(ErrorDesc errorDesc) {
    super(errorDesc.getMessage());
    this.errorDesc = errorDesc;
  }

  public RiskRestException(int code, String message) {
    super(message);
    this.errorDesc = new ErrorDesc(code, this.getMessage());
  }

  public RiskRestException(int code, String message, Throwable cause) {
    super(message, cause);
    this.errorDesc = new ErrorDesc(code, message);
  }

  public ErrorDesc getErrorDesc() {
    return errorDesc;
  }
}
