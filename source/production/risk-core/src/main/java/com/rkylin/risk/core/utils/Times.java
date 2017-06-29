package com.rkylin.risk.core.utils;

import org.joda.time.LocalDate;

/**
 * Created by tomalloc on 16-8-31.
 */
public class Times {
  public static boolean isSameSeason(LocalDate dateTime1, LocalDate dateTime2) {
    if (dateTime1 == null) {
      return false;
    }
    if (dateTime2 == null) {
      return false;
    }
    if (dateTime1 == dateTime2) {
      return true;
    }
    if (dateTime1.getYear() != dateTime2.getYear()) {
      return false;
    }
    int dateTime1Month = dateTime1.getMonthOfYear();
    int dateTime2Month = dateTime2.getMonthOfYear();
    if (dateTime1Month == dateTime2Month) {
      return true;
    }
    return (dateTime1Month - 1) / 3 == (dateTime2Month - 1) / 3;
  }
}
