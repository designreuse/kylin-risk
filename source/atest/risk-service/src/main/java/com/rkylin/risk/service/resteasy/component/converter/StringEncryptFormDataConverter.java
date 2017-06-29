package com.rkylin.risk.service.resteasy.component.converter;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

/**
 * Created by tomalloc on 16-12-2.
 */
public class StringEncryptFormDataConverter extends AbstractEncryptFormDataConverter<String> {
  public StringEncryptFormDataConverter(PublicKey publicKey,Class classType) {
    super(publicKey, classType);
  }

  @Override protected String beforeToString(Object value) {
    return value.toString();
  }

  @Override protected String afterFromString(byte[] decodeBytes) {
    return new String(decodeBytes, StandardCharsets.UTF_8);
  }
}
