package com.rkylin.risk.service.json.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.rkylin.risk.commons.entity.Amount;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-2-15 下午5:41 version: 1.0
 */
@Slf4j
public class AmountDeserializer extends JsonDeserializer<Amount> {
  public AmountDeserializer() {
    super();
  }

  @Override
  public Class<?> handledType() {
    return Amount.class;
  }

  @Override
  public Amount deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    String value = jp.getValueAsString();
    try {
      return new Amount(value);
    } catch (Exception e) {
      log.error("amount反序列化异常,value={}", value, e);
    }
    return null;
  }
}
