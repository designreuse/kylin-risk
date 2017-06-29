package com.rkylin.risk.credit.founder.jackson;

/**
 * Created by tomalloc on 16-7-6.
 */
public interface Encryptor {
  /**
   * 加密数据
   */
  String encrypt(byte[] data);

  /**
   * 解密数据
   */
  byte[] decrypt(String data);
}
