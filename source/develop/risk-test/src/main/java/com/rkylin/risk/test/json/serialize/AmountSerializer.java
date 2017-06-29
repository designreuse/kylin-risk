package com.rkylin.risk.test.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.rkylin.risk.commons.entity.Amount;

import java.io.IOException;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-2-15 下午5:41 version: 1.0
 */
public class AmountSerializer extends JsonSerializer<Amount> {
  public AmountSerializer() {
    super();
  }

  @Override
  public Class<Amount> handledType() {
    return Amount.class;
  }

  @Override
  public void serialize(Amount value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
    //value为null不会进入
    jgen.writeNumber(value.getValue());
  }
}
