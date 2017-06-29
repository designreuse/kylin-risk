package com.rkylin.risk.credit.founder.jackson.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.rkylin.risk.credit.founder.dto.VerifyResult;
import java.io.IOException;

/**
 * Created by tomalloc on 16-7-6.
 */
public class EnumVerifyTypeDeserializer extends JsonDeserializer<VerifyResult> {

  @Override public VerifyResult deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String value = p.getValueAsString();
    if (value != null) {
      return VerifyResult.valueOf(value);
    }
    return null;
  }
}
