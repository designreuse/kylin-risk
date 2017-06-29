package com.rkylin.risk.core.exception;

import com.rkylin.risk.core.exception.model.ReturnCodeMetadata;

/**
 * rest请求数据有误，可能是恶意攻击等
 *
 * @company: rkylin
 * @author: tongzhuyu version: 1.0
 */
public class RiskRestIllegalResourceException extends RiskRestException {
  public RiskRestIllegalResourceException() {
    super(ReturnCodeMetadata.ILLEGAL_RESOURCE.getCode(),
        ReturnCodeMetadata.ILLEGAL_RESOURCE.getMessage());
  }

  public RiskRestIllegalResourceException(String message) {
    super(ReturnCodeMetadata.ILLEGAL_RESOURCE.getCode(), message);
  }

  public RiskRestIllegalResourceException(String message, Throwable cause) {
    super(ReturnCodeMetadata.ILLEGAL_RESOURCE.getCode(), message, cause);
  }
}
