package com.rkylin.risk.boss.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.rkylin.risk.core.enumtype.DateTimePattern;
import java.io.IOException;
import lombok.Setter;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.InitializingBean;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-2-15 下午5:41 version: 1.0
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> implements InitializingBean {

  @Setter
  private String pattern;

  public LocalDateSerializer() {
    super();
  }

  @Override
  public Class<LocalDate> handledType() {
    return LocalDate.class;
  }

  @Override
  public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException {
    //value为null不会进入
    jgen.writeString(value.toString(pattern));
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (pattern == null) {
      pattern = DateTimePattern.DATE.pattern();
    }
  }
}
