package com.rkylin.risk.service.validator.handler;

import com.rkylin.risk.service.validator.core.ResultInfoProxy;

/**
 * Created by tomalloc on 16-4-14.
 */

public class NullValidatorHandler<T> implements Handler {

  private String code;
  private String message;

  private T t;

  public NullValidatorHandler(T t, String code, String message) {
    this.t = t;
    this.code = code;
    this.message = message;
  }

  @Override
  public boolean handle(ResultInfoProxy proxy) {
    if (t == null) {
      proxy.setResultCode(code);
      proxy.setResultMessage(message);
      return false;
    }
    return true;
  }
}
