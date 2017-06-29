package com.rkylin.risk.credit.utils;

/**
 * Created by tomalloc on 16-8-2.
 */
public class Utils {
  private static boolean isSupportDefaultMethod = false;

  static {
    try {
      Class.forName("java.util.Optional");
      isSupportDefaultMethod = true;
    } catch (ClassNotFoundException ignored) {
    }
  }

  public boolean supportDefautMethod() {
    return isSupportDefaultMethod;
  }
}
