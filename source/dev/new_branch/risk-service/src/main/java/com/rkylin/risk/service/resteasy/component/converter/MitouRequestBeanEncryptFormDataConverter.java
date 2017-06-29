package com.rkylin.risk.service.resteasy.component.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.service.resteasy.component.converter.bean.MitouRequestBean;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

/**
 * Created by tomalloc on 16-12-2.
 */
public class MitouRequestBeanEncryptFormDataConverter<T>
    extends ObjectEncryptFormDataConverter<MitouRequestBean> {

  public MitouRequestBeanEncryptFormDataConverter(PublicKey publicKey,
      ObjectMapper objectMapper, Class<T> classType) {
    super(publicKey, objectMapper, classType);
  }

  @Override protected MitouRequestBean afterFromString(byte[] decodeBytes) {
    MitouRequestBean mitouRequestBean = new MitouRequestBean();
    mitouRequestBean.setMessage(new String(decodeBytes, StandardCharsets.UTF_8));
    try {
      Object object = objectMapper.readValue(decodeBytes, classType);
      mitouRequestBean.setBean(object);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return mitouRequestBean;
  }
}
