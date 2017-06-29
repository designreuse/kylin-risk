package com.rkylin.risk.core.utils;

import java.util.UUID;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/10/9 version: 1.0
 */
public class UUIDs {
  private UUIDs() {
  }

  public static String uuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
