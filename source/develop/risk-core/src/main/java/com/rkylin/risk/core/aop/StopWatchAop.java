package com.rkylin.risk.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 耗时统计
 *
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/10/22 version: 1.0
 */
@Slf4j
public class StopWatchAop {

  public Object watchTimeAround(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object returnValue = joinPoint.proceed();
    long endTime = System.currentTimeMillis();
    log.info("class name:{},method name:{},execution time:{}ms",
        joinPoint.getTarget().getClass().getCanonicalName(), joinPoint.getSignature().getName(),
        (endTime - startTime));
    return returnValue;
  }
}
