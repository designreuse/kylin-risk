
package com.rkylin.risk.service.utils;

import java.util.Objects;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

/**
 */
public abstract class DateUtil {
  public static final String PLAIN_PATTERN = "yyyyMMddHHmmss";

  public static DateTime toDateTime(String dateTime, String pattern) {
    Objects.requireNonNull(dateTime);
    Objects.requireNonNull(pattern);
    return DateTime.parse(dateTime, DateTimeFormat.forPattern(pattern));
  }

  public static LocalDate toLocalDate(String localdate, String pattern) {
    if (localdate == null) {
      return null;
    }
    return LocalDate.parse(localdate, DateTimeFormat.forPattern(pattern));
  }

  public static DateTime toDateTime(String dateTime) {
    return toDateTime(dateTime, PLAIN_PATTERN);
  }


}
