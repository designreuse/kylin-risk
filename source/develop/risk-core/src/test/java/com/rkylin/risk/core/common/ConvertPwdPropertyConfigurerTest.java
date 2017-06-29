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
    String key = "jdbc.password";
    String value = "Risk!1";
    String encryptVal = convertPropertyConfigurer.encrypt(key, value);
    assertThat(value).isEqualTo(convertPropertyConfigurer.decrypt(key, encryptVal));
    String redisKey = "jdbc.username";
    String redisValue = "fea96d9d9700a34cddb14db5171286ab1139b1fa6d281bab";
    String v = convertPropertyConfigurer.decrypt(redisKey, redisValue);
    System.out.println(v);
  }
}
