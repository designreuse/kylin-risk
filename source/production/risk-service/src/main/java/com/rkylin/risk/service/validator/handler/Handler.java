package com.rkylin.risk.service.validator.handler;

import com.rkylin.risk.service.validator.core.ResultInfoProxy;

/**
 * Created by tomalloc on 16-4-14.
 */
public interface Handler {
  boolean handle(ResultInfoProxy proxy);
}
