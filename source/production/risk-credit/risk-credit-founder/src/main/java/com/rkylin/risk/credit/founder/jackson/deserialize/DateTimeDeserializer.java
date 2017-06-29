package com.rkylin.risk.credit.founder.jackson.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Objects;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-2-15 下午5:41 version: 1.0
 */
@Slf4j
public class DateTimeDeserializer extends JsonDeserializer<DateTime> {
  public DateTimeDeserializer() {
    this("yyyy-MM-dd HH:mm:ss");
  }

  public DateTimeDeserializer(String pattern) {
    super();
    Objects.requireNonNull(pattern);
    this.pattern = pattern;
  }

  @Override
  public Class<?> handledType() {
    return DateTime.class;
  }

  @Setter
  private String pattern;

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
}
