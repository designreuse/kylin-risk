package com.rkylin.risk.boss.intercept.handler;

import com.rkylin.risk.core.exception.RiskRestException;
import com.rkylin.risk.core.exception.model.ErrorDesc;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import static com.rkylin.risk.core.exception.model.ReturnCodeMetadata.ILLEGAL_RESOURCE_TYPE;
import static com.rkylin.risk.core.exception.model.ReturnCodeMetadata.SYSTEM_ERROR;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/31 version: 1.0
 */
@Slf4j
@ControllerAdvice(basePackages = {"com.rkylin.risk.boss.restController"})
public class RiskRestControllerThrowableHandler {
  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public ErrorDesc handleException(Throwable e, ServletWebRequest request) {
    ErrorDesc errorDesc = null;
    if (e instanceof RiskRestException) {
      RiskRestException restException = (RiskRestException) e;
      errorDesc = restException.getErrorDesc();
    } else if (e instanceof TypeMismatchException) {
      errorDesc = ILLEGAL_RESOURCE_TYPE;
      log.error("类型转换错误", e);
    } else {
      errorDesc = SYSTEM_ERROR;
      log.error("RestException处理错误", e);
    }
    return errorDesc;
  }
}
