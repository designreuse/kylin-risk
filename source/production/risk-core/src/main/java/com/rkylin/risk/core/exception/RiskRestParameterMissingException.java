package com.rkylin.risk.core.exception;

import com.rkylin.risk.core.exception.model.ReturnCodeMetadata;

/**
 * rest请求缺失参数
 *
 * @company: rkylin
 * @author: tongzhuyu version: 1.0
 */
public class RiskRestParameterMissingException extends RiskRestException {

  public RiskRestParameterMissingException() {
    super(ReturnCodeMetadata.PARAMETER_MISSING.getCode(),
        ReturnCodeMetadata.PARAMETER_MISSING.getMessage());
  }

  public RiskRestParameterMissingException(String message) {
    super(ReturnCodeMetadata.PARAMETER_MISSING.getCode(), message);
  }

  public RiskRestParameterMissingException(String message, Throwable cause) {
    super(ReturnCodeMetadata.PARAMETER_MISSING.getCode(), message, cause);
  }
}
