package com.rkylin.risk.credit.founder.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by tomalloc on 16-7-6.
 */
public final class JacksonConverterFactory extends Converter.Factory {
  /** Create an instance using a default {@link ObjectMapper} instance for conversion. */
  public static JacksonConverterFactory create(Encryptor encryptor) {
    return create(new ObjectMapper(), encryptor);
  }

  public static JacksonConverterFactory create() {
    return create(new ObjectMapper(), new DefaultEncryptor());
  }

  /** Create an instance using {@code mapper} for conversion. */
  public static JacksonConverterFactory create(ObjectMapper mapper, Encryptor encryptor) {
    return new JacksonConverterFactory(mapper, encryptor);
  }

  private final ObjectMapper mapper;
  private final Encryptor encryptor;

  private JacksonConverterFactory(ObjectMapper mapper, Encryptor encryptor) {
    if (mapper == null) throw new NullPointerException("mapper == null");
    if (encryptor == null) throw new NullPointerException("encryptor == null");
    this.mapper = mapper;
    this.encryptor = encryptor;
  }

  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
      Retrofit retrofit) {
    JavaType javaType = mapper.getTypeFactory().constructType(type);
    ObjectReader reader = mapper.readerFor(javaType);
    return new JacksonResponseBodyConverter<>(reader, encryptor);
  }

  @Override
  public Converter<?, RequestBody> requestBodyConverter(Type type,
      Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
    JavaType javaType = mapper.getTypeFactory().constructType(type);
    ObjectWriter writer = mapper.writerFor(javaType);
    return new JacksonRequestBodyConverter<>(writer, encryptor);
  }
}
