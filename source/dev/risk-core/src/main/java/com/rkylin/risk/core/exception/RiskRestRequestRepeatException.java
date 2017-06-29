package com.rkylin.risk.core.exception;

import static com.rkylin.risk.core.exception.model.ReturnCodeMetadata.REQUEST_REPEAT;

/**
 * 重复的请求
 *
 * @company: rkylin
 * @author: tongzhuyu version: 1.0
 */
public class RiskRestRequestRepeatException extends RiskRestException {
  public RiskRestRequestRepeatException() {
    super(REQUEST_REPEAT.getCode(), REQUEST_REPEAT.getMessage());
  }

  public RiskRestRequestRepeatException(String message) {
    super(REQUEST_REPEAT.getCode(), message);
  }

  public RiskRestRequestRepeatException(String message, Throwable cause) {
    super(REQUEST_REPEAT.getCode(), message, cause);
  }
}
