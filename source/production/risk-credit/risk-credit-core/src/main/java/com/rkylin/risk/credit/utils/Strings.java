package com.rkylin.risk.credit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by tomalloc on 16-7-28.
 */
public abstract class Strings {
  /**
   * 字符串格式化
   * 如果要格式化:my name is {},{} years.参数为数组{lilei",30}
   * 那么,最终格式化的结果为"my name is lilei,30 years."
   * @param value
   * @param args
   * @return
   */
  public static String format(String value, Object... args) {
    if (value == null) {
      return value;
    }
    if (args == null) {
      return value;
    }
    int arrLength = args.length;
    if (arrLength == 0) {
      return value;
    }
    StringBuilder stringBuilder = new StringBuilder();
    Matcher m = Pattern.compile("\\{\\}").matcher(value);
    int index = 0;
    int subStringBeginIndex = 0;
    while (index < arrLength && m.find()) {
      int currentStartIndex = m.start();
      stringBuilder.append(value.substring(subStringBeginIndex, currentStartIndex));
      stringBuilder.append(args[index]);
      subStringBeginIndex = m.end();
      index++;
    }
    if (subStringBeginIndex < value.length()) {
      stringBuilder.append(value.substring(subStringBeginIndex));
    }
    return stringBuilder.toString();
  }
}
