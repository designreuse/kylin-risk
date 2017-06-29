package com.rkylin.risk.boss.json.deserialize;

import com.rkylin.risk.core.enumtype.DateTimePattern;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-2-15 下午5:41 version: 1.0
 */
@Slf4j
public class DateTimeDeserializer extends JsonDeserializer<DateTime> implements InitializingBean {
  @Setter
  private String pattern;

  public DateTimeDeserializer() {
    super();
  }

  @Override
  public Class<?> handledType() {
    return DateTime.class;
  }

  @Override
  public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    String value = jp.getValueAsString();
    try {
      return DateTime.parse(value, DateTimeFormat.forPattern(pattern));
    } catch (Exception e) {
      log.error("DateTime反序列化异常,value={}", value, e);
    }
    return null;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (pattern == null) {
      pattern = DateTimePattern.DATETIME.pattern();
    }
  }
}
