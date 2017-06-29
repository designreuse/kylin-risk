package com.rkylin.risk.credit.founder.jackson;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by tomalloc on 16-7-6.
 */
public class DesEncryptor implements Encryptor {
  private final byte[] keys;

  public DesEncryptor(byte[] keys) {
    if (keys == null) throw new NullPointerException("keys == null");
    this.keys = keys;
  }

  @Override public String encrypt(byte[] data) {
    byte[] encryption;
    try {
      encryption = DesUtils.encrypt(data, keys);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return DatatypeConverter.printBase64Binary(encryption);
  }

  @Override public byte[] decrypt(String data) {
    byte[] decodeBase64 = DatatypeConverter.parseBase64Binary(data);
    try {
      return DesUtils.decrypt(decodeBase64, keys);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
