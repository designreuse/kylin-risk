package com.rkylin.risk.core.utils;

import com.rkylin.risk.commons.entity.Amount;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by tomalloc on 16-4-8.
 */
public class ObjectUtils {

  public static Integer intValueOf(String value, Integer secondValue) {
    return isEmpty(value) ? secondValue : Integer.valueOf(value);
  }

  public static Amount amountValueOf(String value, Amount secondValue) {
    return isEmpty(value) ? secondValue : new Amount(value);
  }

  public static Amount amountValueOf(Integer value, Amount secondValue) {
    return value == null ? secondValue : new Amount(value);
  }
}
