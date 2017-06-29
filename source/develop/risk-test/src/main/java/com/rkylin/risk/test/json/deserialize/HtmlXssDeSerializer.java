package com.rkylin.risk.test.json.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-3-5 下午3:08 version: 1.0
 */
public class HtmlXssDeSerializer extends JsonDeserializer<String> {

  @Override
  public Class<?> handledType() {
    return String.class;
  }

  @Override
  public String deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String value = jp.getValueAsString();
    if (value != null) {
      value = StringEscapeUtils.escapeXml10(value);
    }
    return value;
  }
}
