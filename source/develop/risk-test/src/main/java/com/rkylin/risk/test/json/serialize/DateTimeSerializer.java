package com.rkylin.risk.test.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.rkylin.risk.core.enumtype.DateTimePattern;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-2-15 下午5:41 version: 1.0
 */
public class DateTimeSerializer extends JsonSerializer<DateTime> implements InitializingBean {

  @Setter
  private String pattern;

  public DateTimeSerializer() {
    super();
  }

  @Override
  public Class<DateTime> handledType() {
    return DateTime.class;
  }

  @Override
  public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
    //value为null不会进入
    jgen.writeString(value.toString(pattern));
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (pattern == null) {
      pattern = DateTimePattern.DATETIME.pattern();
    }
  }
}
