package com.rkylin.risk.service.credit;

import com.rkylin.risk.core.exception.RiskRestException;
import com.rkylin.risk.core.exception.model.ErrorDesc;

/**
 * Created by tomalloc on 16-12-14.
 */
public class RiskCreditTokenErrorException extends RiskRestException {
  public RiskCreditTokenErrorException(ErrorDesc errorDesc) {
    super(errorDesc);
  }
  public RiskCreditTokenErrorException(int code, String message) {
    super(code,message);
  }

  public RiskCreditTokenErrorException(int code, String message, Throwable cause) {
    super(code, message,cause);
  }
}
