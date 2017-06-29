package com.rkylin.risk.boss.resteasy.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.boss.resteasy.EncryptParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.xml.bind.DatatypeConverter;

/**
 * 需要 rsa 公钥加密的字段,需要实现 EncryptParam 接口,程序会自动转换为加密的数据 Created by tomalloc on 16-11-2.
 */
public class EncryptFormDataConverterProvider implements ParamConverterProvider {

  private final EncryptFormDataConverter converter;

  public EncryptFormDataConverterProvider(String publicKey) {
    this(publicKey, new ObjectMapper());
  }

  public EncryptFormDataConverterProvider(String rsaPublicKey, ObjectMapper objectMapper) {
    Objects.requireNonNull(rsaPublicKey);
    Objects.requireNonNull(objectMapper);
    KeyFactory kf = null;
    try {
      X509EncodedKeySpec spec =
          new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(rsaPublicKey));
      kf = KeyFactory.getInstance("RSA");
      PublicKey publicKey = kf.generatePublic(spec);
      this.converter = new EncryptFormDataConverter(publicKey, objectMapper);
    } catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  @Override public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType,
      Annotation[] annotations) {
    if (EncryptParam.class.isAssignableFrom(rawType)) {
      return (ParamConverter<T>) converter;
    }
    return null;
  }

  /**
   * 实现 EncryptParam 接口的 url 请求参数用 rsa 公钥加密
   */
  private static class EncryptFormDataConverter implements ParamConverter<Object> {
    final ObjectMapper objectMapper;
    final PublicKey publicKey;

    static final ThreadLocal<Cipher> cipherThreadLocal = new ThreadLocal<Cipher>() {
      protected Cipher initialValue() {
        try {
          return Cipher.getInstance("RSA");
        } catch (final NoSuchAlgorithmException | NoSuchPaddingException e) {
          throw new RuntimeException(e);
        }
      }
    };

    EncryptFormDataConverter(PublicKey publicKey, ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
      this.publicKey = publicKey;
    }

    private void splitEncrypt(String string, StringBuilder stringBuilder)
        throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
      Cipher cipher = cipherThreadLocal.get();
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] output = cipher.doFinal(string.getBytes(StandardCharsets.UTF_8));
      stringBuilder.append(DatatypeConverter.printBase64Binary(output));
      stringBuilder.append("|");
    }

    @Override public Object fromString(String value) {
      return null;
    }

    @Override public String toString(Object value) {
      try {
        String str = objectMapper.writeValueAsString(value);
        String encodeBase64 =
            DatatypeConverter.printBase64Binary(str.getBytes(StandardCharsets.UTF_8));
        int base64Length = encodeBase64.length();
        int per = 96;
        int blockTotal = base64Length / per;

        StringBuilder stringBuilder = new StringBuilder(256);
        for (int i = 0; i < blockTotal; i++) {
          int beginIndex = i * per;
          String fieldData = encodeBase64.substring(beginIndex, beginIndex + per);
          splitEncrypt(fieldData, stringBuilder);
        }
        int dy = base64Length % per;
        if (dy > 0) {
          String fieldData = encodeBase64.substring(blockTotal * per);
          splitEncrypt(fieldData, stringBuilder);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      } catch (BadPaddingException e) {
        throw new RuntimeException(e);
      } catch (IllegalBlockSizeException e) {
        throw new RuntimeException(e);
      } catch (InvalidKeyException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
