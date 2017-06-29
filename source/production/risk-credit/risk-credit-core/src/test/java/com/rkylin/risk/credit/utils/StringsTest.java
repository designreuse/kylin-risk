package com.rkylin.risk.credit.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-7-28.
 */
public class StringsTest {
  @Test
  public void formatEnglishTest() {
    String strings = "my name is {},{} years.";
    Object[] args = {"lilei", 30};
    String expectValue = "my name is lilei,30 years.";
    assertThat(expectValue).isEqualTo(Strings.format(strings, args));
  }

  @Test
  public void formatChineseTest() {
    String strings = "我是{},我在{}工作";
    Object[] args = {"中国人", "北京"};
    String expectValue = "我是中国人,我在北京工作";
    assertThat(expectValue).isEqualTo(Strings.format(strings, args));
  }

  @Test
  public void formatNoneArgsTest() {
    String strings = "我是{},我在{}工作";
    assertThat(strings).isEqualTo(Strings.format(strings));
  }

  @Test
  public void formatEmptyArgsTest() {
    String strings = "我是{},我在{}工作";
    assertThat(strings).isEqualTo(Strings.format(strings, new Object[0]));
  }

  @Test
  public void formatNotMatchArgs1Test() {
    String strings = "我是{},我在{}工作";
    Object[] args = {"中国人"};
    String expectValue = "我是中国人,我在{}工作";
    assertThat(strings).isEqualTo(Strings.format(strings));
  }

  @Test
  public void formatNotMatchArgs2Test() {
    String strings = "我是{},我在{}工作";
    Object[] args = {"中国人", "北京", "哈哈"};
    String expectValue = "我是中国人,我在北京工作";
    assertThat(strings).isEqualTo(Strings.format(strings));
  }

  @Test
  public void formatAllArgsTest() {
    String strings = "{}{},{}{}{}";
    Object[] args = {"我是", "中国人", "我在", "北京", "工作"};
    String expectValue = "我是中国人,我在北京工作";
    assertThat(strings).isEqualTo(Strings.format(strings));
  }
}
