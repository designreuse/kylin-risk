package com.rkylin.risk.credit.founder.jackson;

import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by tomalloc on 16-7-6.
 */
final class JacksonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final ObjectReader adapter;

  private final Encryptor encryptor;

  JacksonResponseBodyConverter(ObjectReader adapter, Encryptor encryptor) {
    this.adapter = adapter;
    this.encryptor = encryptor;
  }

  @Override public T convert(ResponseBody value) throws IOException {
    try {
      String sbOutput = value.string();
      byte[] decryptData = encryptor.decrypt(sbOutput);
      return adapter.readValue(decryptData);
    } finally {
      value.close();
    }
  }
}