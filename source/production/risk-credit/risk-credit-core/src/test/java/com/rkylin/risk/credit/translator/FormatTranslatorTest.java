package com.rkylin.risk.credit.translator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-6-29.
 */
public class FormatTranslatorTest {
  @Test
  public void translatorTest() {
    FormatTranslator translator = new FormatTranslator("{}KM");
    String formatStr = translator.translator("5");
    assertThat(formatStr).isEqualTo("5KM");
  }
}
