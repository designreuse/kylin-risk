package com.rkylin.risk.core.enumtype;

/**
 * 日期格式枚举
 *
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/6 version: 1.0
 */
public enum DateTimePattern {

  DATETIME("yyyy-MM-dd HH:mm:ss.SSS"),

  DATE("yyyy-MM-dd"),

  TIME("HH:mm:ss.SSS");

  private String pattern;

  DateTimePattern(String pattern) {
    this.pattern = pattern;
  }

  public String pattern() {
    return pattern;
  }
}
