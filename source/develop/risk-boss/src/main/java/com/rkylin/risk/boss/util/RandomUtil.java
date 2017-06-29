package com.rkylin.risk.boss.util;

import java.security.SecureRandom;

/**
 * Created by 201508031790 on 2015/9/9.
 */
public class RandomUtil {

  private static final String BUFFER =
      "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  private static final int DEFAULT_RANDOM_LENGTH = 6;

  public static String random() {
    return random(DEFAULT_RANDOM_LENGTH);
  }

  public static String random(int length) {
    StringBuilder sb = new StringBuilder();
    SecureRandom r = new SecureRandom();
    int range = BUFFER.length();
    for (int i = 0; i < length; i++) {
      sb.append(BUFFER.charAt(r.nextInt(range)));
    }
    return sb.toString();
  }
}
