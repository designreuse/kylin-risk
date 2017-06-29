package com.rkylin.risk.service.resteasy.component.converter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.ext.ParamConverter;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by tomalloc on 16-12-2.
 */
@Slf4j
public abstract class AbstractEncryptFormDataConverter<T> implements ParamConverter<T> {
  final PublicKey publicKey;
  final Class<T> classType;
  final boolean isEncoded;

  static final ThreadLocal<javax.crypto.Cipher> decryptCipherThreadLocal =
      new ThreadLocal<Cipher>() {
        protected Cipher initialValue() {
          try {
            return Cipher.getInstance("RSA");
          } catch (final NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
          }
        }
      };

  static final ThreadLocal<Cipher> encryptCipherThreadLocal = new ThreadLocal<Cipher>() {
    protected Cipher initialValue() {
      try {
        return Cipher.getInstance("RSA");
      } catch (final NoSuchAlgorithmException | NoSuchPaddingException e) {
        throw new RuntimeException(e);
      }
    }
  };

  public AbstractEncryptFormDataConverter(PublicKey publicKey, Class classType,boolean isEncoded) {
    this.publicKey = publicKey;
    this.classType = classType;
    this.isEncoded=isEncoded;
  }

  private void splitEncrypt(String string, StringBuilder stringBuilder)
      throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    Cipher cipher = encryptCipherThreadLocal.get();
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] output = cipher.doFinal(string.getBytes(StandardCharsets.UTF_8));
    stringBuilder.append(DatatypeConverter.printBase64Binary(output));
    stringBuilder.append("|");
  }

  @Override public T fromString(String value) {
    if (StringUtils.isBlank(value)) {
      return null;
    }
    if (log.isDebugEnabled()) {
      log.debug("receive data:{}", value);
    }
    if(isEncoded){
      try {
        value=URLDecoder.decode(value,"UTF-8");
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }
    value = value.replace(" ", "+");
    String[] values = value.split("\\|");
    Cipher cipher = decryptCipherThreadLocal.get();
    StringBuilder sw = new StringBuilder();
    try {
      cipher.init(Cipher.DECRYPT_MODE, publicKey);
      for (String segment : values) {
        byte[] segmentDecode = DatatypeConverter.parseBase64Binary(segment);
        String segmentDecryptStr = new String(cipher.doFinal(segmentDecode));
        sw.append(segmentDecryptStr);
      }
    } catch (InvalidKeyException e) {
      throw new RuntimeException(e);
    } catch (BadPaddingException e) {
      throw new RuntimeException(e);
    } catch (IllegalBlockSizeException e) {
      throw new RuntimeException(e);
    }
    byte[] decodeBytes = DatatypeConverter.parseBase64Binary(sw.toString());
    if (log.isDebugEnabled()) {
      log.debug("decrypt data:{}", new String(decodeBytes, StandardCharsets.UTF_8));
    }
    return afterFromString(decodeBytes);
  }

  protected abstract String beforeToString(Object value);

  protected abstract T afterFromString(byte[] decodeBytes);

  @Override public String toString(Object value) {
    if (value == null) {
      return null;
    }
    try {
      String str = beforeToString(value);
      if (log.isDebugEnabled()) {
        log.debug("send data:{}", str);
      }
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
      String encryptData = stringBuilder.substring(0, stringBuilder.length() - 1);
      if (log.isDebugEnabled()) {
        log.debug("encrypt data:{}", encryptData);
      }
      if(isEncoded){
        encryptData= URLEncoder.encode(encryptData,"UTF-8");
      }
      return encryptData;
    } catch (BadPaddingException e) {
      throw new RuntimeException(e);
    } catch (IllegalBlockSizeException e) {
      throw new RuntimeException(e);
    } catch (InvalidKeyException e) {
      throw new RuntimeException(e);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
