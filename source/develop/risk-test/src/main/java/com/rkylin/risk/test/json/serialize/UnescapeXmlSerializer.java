package com.rkylin.risk.test.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-6-1 上午11:28 version: 1.0
 */
public class UnescapeXmlSerializer extends JsonSerializer<String> {

  public UnescapeXmlSerializer() {
    super();
  }

  @Override
  public Class<String> handledType() {
    return String.class;
  }

  @Override
  public void serialize(String value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
    if (StringUtils.isNotBlank(value)) {
      jgen.writeString(StringEscapeUtils.unescapeXml(value));
    }
  }
}
