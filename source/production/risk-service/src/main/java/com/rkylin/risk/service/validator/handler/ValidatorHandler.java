package com.rkylin.risk.service.validator.handler;

/**
 * Created by tomalloc on 16-4-15.
 */
public interface ValidatorHandler<T> extends Handler {
  /**
   * 验证对象 懒加载验证对象
   */
  T validateObject();
}
