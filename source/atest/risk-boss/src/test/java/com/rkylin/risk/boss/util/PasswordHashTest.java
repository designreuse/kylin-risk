package com.rkylin.risk.boss.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-2-2.
 */
public class PasswordHashTest {
  @Test
  public void testHash() throws InvalidKeySpecException, NoSuchAlgorithmException {
    String password = RandomUtil.random();
    String hash = PasswordHash.createHash(password);
    assertThat(PasswordHash.validatePassword(password, hash)).isTrue();
  }
}
