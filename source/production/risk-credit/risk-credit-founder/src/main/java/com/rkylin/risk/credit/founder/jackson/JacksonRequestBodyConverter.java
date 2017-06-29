package com.rkylin.risk.credit.founder.jackson;

import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by tomalloc on 16-7-6.
 */
final class JacksonRequestBodyConverter<T> implements Converter<T, RequestBody> {
  private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

  private final ObjectWriter adapter;

  private final Encryptor encryptor;

  JacksonRequestBodyConverter(ObjectWriter adapter, Encryptor encryptor) {
    this.adapter = adapter;
    this.encryptor = encryptor;
  }

  @Override public RequestBody convert(T value) throws IOException {
    byte[] bytes = adapter.writeValueAsBytes(value);
    String encryptData = encryptor.encrypt(bytes);
    return RequestBody.create(MEDIA_TYPE, encryptData);
  }
}