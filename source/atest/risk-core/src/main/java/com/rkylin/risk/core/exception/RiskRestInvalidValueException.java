package com.rkylin.risk.core.exception;

import com.rkylin.risk.core.exception.model.ReturnCodeMetadata;

/**
 * 值无效，格式错误、范围不正确等
 *
 * @company: rkylin
 * @author: tongzhuyu version: 1.0
 */
public class RiskRestInvalidValueException extends RiskRestException {
  public RiskRestInvalidValueException() {
    super(ReturnCodeMetadata.INVALID_VALUE.getCode(),
        ReturnCodeMetadata.INVALID_VALUE.getMessage());
  }

  public RiskRestInvalidValueException(String message) {
    super(ReturnCodeMetadata.INVALID_VALUE.getCode(), message);
  }

  public RiskRestInvalidValueException(String message, Throwable cause) {
    super(ReturnCodeMetadata.INVALID_VALUE.getCode(), message, cause);
  }
}
