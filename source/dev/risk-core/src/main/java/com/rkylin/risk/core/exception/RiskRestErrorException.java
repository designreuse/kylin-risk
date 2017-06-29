package com.rkylin.risk.core.exception;

import static com.rkylin.risk.core.exception.model.ReturnCodeMetadata.SYSTEM_ERROR;

/**
 * @company: rkylin
 * @author: tongzhuyu version: 1.0
 */
public class RiskRestErrorException extends RiskRestException {
  public RiskRestErrorException() {
    super(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getMessage());
  }

  public RiskRestErrorException(String message) {
    super(SYSTEM_ERROR.getCode(), message);
  }

  public RiskRestErrorException(String message, Throwable cause) {
    super(SYSTEM_ERROR.getCode(), message, cause);
  }
}
