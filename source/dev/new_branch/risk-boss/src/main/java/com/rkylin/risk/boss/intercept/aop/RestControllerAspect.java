package com.rkylin.risk.boss.intercept.aop;

import com.rkylin.risk.core.exception.RiskRestObjectIsNullException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/31 version: 1.0
 */
@Aspect
public class RestControllerAspect {

  @AfterReturning(
      pointcut = "execution(public * com.rkylin.risk.boss.restController.*RestAction.*(..))",
       returning = "result")
  public void doAfterReturning(JoinPoint joinPoint, Object result) {
    if (result == null) {
      throw new RiskRestObjectIsNullException();
    }
  }
}
