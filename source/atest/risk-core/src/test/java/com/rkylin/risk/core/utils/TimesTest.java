package com.rkylin.risk.core.utils;

import org.assertj.core.api.AbstractBooleanAssert;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-8-31.
 */
public class TimesTest {

  @Test
  public void isSameSeason() {

    for (int i = 1; i < 13; i++) {
      DateTime dateTime = new DateTime(2016, i, 1, 1, 1);
      for (int j = i; j < 13; j++) {
        AbstractBooleanAssert booleanAssert =
            assertThat(Times.isSameSeason(dateTime, new DateTime(2016, j, 1, 1, 1)));
        boolean flag = false;
        if (i >= 1 && i <= 3 && j >= 1 && j <= 3) {
          flag = true;
        } else if (i >= 4 && i <= 6 && j >= 4 && j <= 6) {
          flag = true;
        } else if (i >= 7 && i <= 9 && j >= 7 && j <= 9) {
          flag = true;
        } else if (i >= 10 && i <= 12 && j >= 10 && j <= 12) {
          flag = true;
        }
        System.out.println(i + "," + j + " is same season :" + flag);
        if (flag) {
          booleanAssert.isTrue();
        } else {
          booleanAssert.isFalse();
        }
      }
    }
  }
}
