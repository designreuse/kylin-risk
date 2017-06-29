package com.rkylin.risk.credit.excel.annotations;

/**
 * Created by tomalloc on 16-6-28.
 */
public @interface ExcelSheet {
  int index() default 0;

  String name();

  int startRowIndex() default 0;
}
