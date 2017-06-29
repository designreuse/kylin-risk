package com.rkylin.risk.credit.founder.jackson;

import java.nio.charset.StandardCharsets;

/**
 * Created by tomalloc on 16-7-6.
 */
public class DefaultEncryptor implements Encryptor {
  @Override public String encrypt(byte[] data) {
    return new String(data);
  }

  @Override public byte[] decrypt(String data) {
    return data.getBytes(StandardCharsets.UTF_8);
  }
}
