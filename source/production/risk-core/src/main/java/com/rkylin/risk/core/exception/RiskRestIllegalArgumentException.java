package com.rkylin.risk.core.exception;

import static com.rkylin.risk.core.exception.model.ReturnCodeMetadata.ILLEGAL_ARGUMENT;

/**
 * rest请求参数错误
 *
 * @company: rkylin
 * @author: tongzhuyu version: 1.0
 */
public class RiskRestIllegalArgumentException extends RiskRestException {
  public RiskRestIllegalArgumentException() {
    super(ILLEGAL_ARGUMENT.getCode(), ILLEGAL_ARGUMENT.getMessage());
  }

  public RiskRestIllegalArgumentException(String message) {
    super(ILLEGAL_ARGUMENT.getCode(), message);
  }

  public RiskRestIllegalArgumentException(String message, Throwable cause) {
    super(ILLEGAL_ARGUMENT.getCode(), message, cause);
  }
}
