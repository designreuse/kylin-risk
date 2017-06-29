package com.rkylin.risk.service.resteasy.component.converter;

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
import org.apache.commons.lang3.StringUtils;

/**
 * Created by tomalloc on 16-12-2.
 */
public abstract class AbstractEncryptFormDataConverter<T> implements ParamConverter<T> {
  final PublicKey publicKey;
  final Class<T> classType;

  static final ThreadLocal<javax.crypto.Cipher> decryptCipherThreadLocal = new ThreadLocal<Cipher>() {
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

  public AbstractEncryptFormDataConverter(PublicKey publicKey,Class classType) {
    this.publicKey = publicKey;
    this.classType=classType;
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
    if(StringUtils.isBlank(value)){
      return null;
    }
    value=value.replace(" ", "+");
    String[] values = value.split("\\|");
    Cipher cipher = decryptCipherThreadLocal.get();
    StringBuilder sw = new StringBuilder();
    try {
      cipher.init(Cipher.DECRYPT_MODE, publicKey);
      for (String segment : values) {
        byte[] segmentDecode=DatatypeConverter.parseBase64Binary(segment);
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
    byte[] decodeBytes=DatatypeConverter.parseBase64Binary(sw.toString());
    return afterFromString(decodeBytes);
  }

  protected abstract String beforeToString(Object value);
  protected abstract T afterFromString(byte[] decodeBytes);

  @Override public String toString(Object value) {
    if(value==null){return null;}
    try {
      String str=beforeToString(value);
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
    }catch (BadPaddingException e) {
      throw new RuntimeException(e);
    } catch (IllegalBlockSizeException e) {
      throw new RuntimeException(e);
    } catch (InvalidKeyException e) {
      throw new RuntimeException(e);
    }
  }
}
