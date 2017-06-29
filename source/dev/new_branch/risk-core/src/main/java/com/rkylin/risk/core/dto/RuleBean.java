package com.rkylin.risk.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

/**
 * Created by lina on 2016-7-14.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RuleBean {
  private String rulehisid;

  private String rulename;

  private String result;

  private String priority;

  private String[] fields;

  private String[] conditions;

  private String[] conditionvals;

  private String[] logicsym;

  /**
   * 判断是否是数字
   */
  public static boolean isNumeric(String s) {
    if ("".equals(s)) {
      return false;
    } else {
      return StringUtils.isNumeric(s);
    }
  }

  /**
   * 将首字母大写
   */
  public static String firstLetterToUpper(String str) {
    char[] cs = str.toCharArray();
    cs[0] -= 32;
    return String.valueOf(cs);
  }

  /**
   * 判断是否为负数
   */
  public static boolean isNegaNum(String str) {
    return str.matches("-[0-9]+.*[0-9]*");
  }

  public static boolean isEndWithRightbrac(String str) {
    return str.endsWith(")");
  }

  public static boolean isStartWithLeftbrac(String str) {
    return str.startsWith("(");
  }

  public static boolean isNotNullVal(String str) {
    return !StringUtils.isEmpty(str) && !"(".equals(str) && !")".equals(str);
  }

  public static String subBracBefore(String str) {
    return StringUtils.substringBefore(str, ")");
  }

  public static String subBracAfter(String str) {
    return StringUtils.substringAfter(str, "(");
  }
}
