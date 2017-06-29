package com.rkylin.risk.service.validator.core;

import com.rkylin.risk.service.validator.builder.Builder;
import com.rkylin.risk.service.validator.handler.Handler;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tomalloc on 16-4-15.
 */
public class Validator {
  private Validator() {
  }

  private List<Handler> validatorHandlers = new LinkedList<Handler>();
  private Builder builder;

  public static Validator validator(Builder builder) {
    Validator validator = new Validator();
    validator.builder = builder;
    return validator;
  }

  public Validator on(Handler handler) {
    if (handler != null) {
      validatorHandlers.add(handler);
    }
    return this;
  }

  public <T extends Builder> ValidateResult<T> doValidate(ResultInfoProxy proxy) {
    ValidateResult validateResult = new ValidateResult();
    boolean pass = true;
    for (Handler validatorHandler : validatorHandlers) {
      if (!validatorHandler.handle(proxy)) {
        pass = false;
        break;
      }
    }
    validateResult.setPass(pass);
    validateResult.setBuilder(builder);
    return validateResult;
  }
}
