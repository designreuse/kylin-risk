package com.rkylin.risk.credit.translator;

import com.rkylin.risk.credit.utils.Strings;

/**
 * 模板字符串的格式化
 * 用于给值添加单位或其他的格式化操作
 * Created by tomalloc on 16-6-29.
 */
public class FormatTranslator implements ValueTranslator {
  private final String formatString;

  public FormatTranslator(String formatString) {
    this.formatString = formatString;
  }

  @Override public String translator(String value) {
    return Strings.format(formatString, value);
  }
}
