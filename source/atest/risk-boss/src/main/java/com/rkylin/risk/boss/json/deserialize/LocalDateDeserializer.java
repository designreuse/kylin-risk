package com.rkylin.risk.boss.json.deserialize;

import com.rkylin.risk.core.enumtype.DateTimePattern;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/6 version: 1.0
 */
@Slf4j
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> implements InitializingBean {

  public LocalDateDeserializer() {
    super();
  }

  @Override
  public Class<?> handledType() {
    return LocalDate.class;
  }

  @Setter
  private String pattern;

  @Override
  public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    String value = jp.getValueAsString();
    try {
      return LocalDate.parse(value, DateTimeFormat.forPattern(pattern));
    } catch (Exception e) {
      log.error("DateTime反序列化异常,value={}", value, e);
    }
    return null;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (pattern == null) {
      pattern = DateTimePattern.DATE.pattern();
    }
  }
}
