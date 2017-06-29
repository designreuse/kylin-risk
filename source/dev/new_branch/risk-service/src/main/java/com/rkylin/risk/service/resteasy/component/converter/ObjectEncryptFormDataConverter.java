package com.rkylin.risk.service.resteasy.component.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.PublicKey;

/**
 * Created by tomalloc on 16-12-2.
 */
public class ObjectEncryptFormDataConverter<T> extends AbstractEncryptFormDataConverter<T> {
  final ObjectMapper objectMapper;
  public ObjectEncryptFormDataConverter(PublicKey publicKey, ObjectMapper objectMapper,Class classType) {
    super(publicKey,classType);
    this.objectMapper = objectMapper;
  }

  @Override protected String beforeToString(Object value){
    try {
      return objectMapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override protected T afterFromString(byte[] decodeBytes) {
    try {
      return (T) objectMapper.readValue(decodeBytes,classType);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
