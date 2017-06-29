package com.rkylin.risk.service.resteasy.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.service.resteasy.ClientEncoded;
import com.rkylin.risk.service.resteasy.Encrypt;
import com.rkylin.risk.service.resteasy.EncryptParam;
import com.rkylin.risk.service.resteasy.component.converter.MitouRequestBeanEncryptFormDataConverter;
import com.rkylin.risk.service.resteasy.component.converter.ObjectEncryptFormDataConverter;
import com.rkylin.risk.service.resteasy.component.converter.StringEncryptFormDataConverter;
import com.rkylin.risk.service.resteasy.component.converter.bean.MitouRequestBean;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

/**
 * 需要 rsa 公钥加密的字段,需要实现 EncryptParam 接口,程序会自动转换为加密的数据 Created by tomalloc on 16-11-2.
 */
@Provider
public class EncryptFormDataConverterProvider implements ParamConverterProvider {

  private  PublicKey publicKey;
  private  ObjectMapper objectMapper;

  public EncryptFormDataConverterProvider(String rsaPublicKey) {
    this.publicKey=initPublicKey(rsaPublicKey);
    this.objectMapper=new ObjectMapper();
  }

  private PublicKey initPublicKey(String rsaPublicKey){
    Objects.requireNonNull(rsaPublicKey);
    KeyFactory kf = null;
    try {
      X509EncodedKeySpec spec =
          new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(rsaPublicKey));
      kf = KeyFactory.getInstance("RSA");
      return kf.generatePublic(spec);
    } catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  public EncryptFormDataConverterProvider(String rsaPublicKey, ObjectMapper objectMapper) {
    Objects.requireNonNull(objectMapper);
    this.publicKey=initPublicKey(rsaPublicKey);
    this.objectMapper = objectMapper;

  }

  @Override public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType,
      Annotation[] annotations) {
    if (annotations != null) {
      for (Annotation annotation : annotations) {
        if (annotation.annotationType() == Encrypt.class) {
          return matchConverter(rawType, genericType, annotations);
        }
      }
    }
    if (EncryptParam.class.isAssignableFrom(rawType)) {
      return matchConverter(rawType, genericType, annotations);
    }
    return null;
  }

  private ParamConverter matchConverter(Class rawType, Type genericType,
      Annotation[] annotations) {
    boolean isEncoded = ClientEncoded.class.isAssignableFrom(rawType);
    if(CharSequence.class.isAssignableFrom(rawType)){
      return new StringEncryptFormDataConverter(publicKey,rawType,isEncoded);
    }

    Objects.requireNonNull(objectMapper);
    if(rawType== MitouRequestBean.class){
      if(genericType instanceof ParameterizedType){
        ParameterizedType parameterizedType= (ParameterizedType) genericType;
        Type[] types=parameterizedType.getActualTypeArguments();
        if(types!=null&&types.length==1){
          Type parameterType=types[0];
          if(parameterType instanceof Class){
            Class parameterizedClass=(Class) parameterType;
            return new MitouRequestBeanEncryptFormDataConverter(publicKey,objectMapper,parameterizedClass,isEncoded);
          }
        }
      }
    }
    return new ObjectEncryptFormDataConverter(publicKey,objectMapper,rawType,isEncoded);
  }
}
