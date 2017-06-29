package com.rkylin.risk.core.exception;

import com.rkylin.risk.core.exception.model.ReturnCodeMetadata;

/**
 * @company: rkylin
 * @author: tongzhuyu version: 1.0
 */
public class RiskRestObjectIsNullException extends RiskRestException {
  public RiskRestObjectIsNullException() {
    super(ReturnCodeMetadata.OBJECT_IS_NULL.getCode(),
        ReturnCodeMetadata.OBJECT_IS_NULL.getMessage());
  }

  public RiskRestObjectIsNullException(String message) {
    super(ReturnCodeMetadata.OBJECT_IS_NULL.getCode(), message);
  }

  public RiskRestObjectIsNullException(String message, Throwable cause) {
    super(ReturnCodeMetadata.OBJECT_IS_NULL.getCode(), message, cause);
  }
}
