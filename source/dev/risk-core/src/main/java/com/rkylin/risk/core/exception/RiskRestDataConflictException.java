package com.rkylin.risk.core.exception;

import com.rkylin.risk.core.exception.model.ReturnCodeMetadata;

/**
 * 请求数据据冲突数据冲突
 *
 * @company: rkylin
 * @author: tongzhuyu version: 1.0
 */
public class RiskRestDataConflictException extends RiskRestException {
  public RiskRestDataConflictException() {
    super(ReturnCodeMetadata.DATA_CONFLICT.getCode(),
        ReturnCodeMetadata.DATA_CONFLICT.getMessage());
  }

  public RiskRestDataConflictException(String message) {
    super(ReturnCodeMetadata.DATA_CONFLICT.getCode(), message);
  }

  public RiskRestDataConflictException(String message, Throwable cause) {
    super(ReturnCodeMetadata.DATA_CONFLICT.getCode(), message, cause);
  }
}
