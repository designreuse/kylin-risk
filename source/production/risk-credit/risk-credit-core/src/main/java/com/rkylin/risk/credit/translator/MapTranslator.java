package com.rkylin.risk.credit.translator;

import java.util.Map;

/**
 * Created by tomalloc on 16-6-28.
 */
public class MapTranslator implements ValueTranslator {
  private Map<String, String> map;

  public MapTranslator(Map<String, String> map) {
    this.map = map;
  }

  @Override public String translator(String value) {
    if (map.containsKey(value)) {
      return map.get(value);
    }
    return "";
  }
}
