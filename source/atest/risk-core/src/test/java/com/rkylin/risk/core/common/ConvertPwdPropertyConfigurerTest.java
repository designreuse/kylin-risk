package com.rkylin.risk.core.common;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/11 version: 1.0
 */
public class ConvertPwdPropertyConfigurerTest {

  @Test
  public void testEncryptAndDecrypt() throws Exception {
    CustomPropertyPlaceHoderConfigurer
        convertPropertyConfigurer = new CustomPropertyPlaceHoderConfigurer();
    String key = "jdbc_report.password";
    //String key = "jdbc_report.username";
    String value = "Queryuser@!1";
    String encryptVal = convertPropertyConfigurer.encrypt(key, value);
    System.out.println(encryptVal);
    assertThat(value).isEqualTo(convertPropertyConfigurer.decrypt(key, encryptVal));
    String redisKey = "jdbc.username";
    String redisValue = "d185a2360467b117";
    String v = convertPropertyConfigurer.decrypt(redisKey, redisValue);
    System.out.println(v);
  }


}
