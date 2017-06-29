package com.rkylin.risk.spring.handler;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

/**
 * url和view的HanlderMapping实现
 */
public class SimpleUrlViewHandlerMapping extends AbstractHandlerMapping {
  private Map<String, ModelAndView> urlViewMapping = new LinkedHashMap<>();

  public void setUrlViewMapping(Properties props) {
    if (props != null) {
      for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
        String key = (String) en.nextElement();
        String value = props.getProperty(key);
        if (value == null) {
          value = (String) props.get(key);
        }
        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
          urlViewMapping.put(key, new ModelAndView(value));
        }
      }
    }
  }

  @Override protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
    String lookupPath = getUrlPathHelper().getLookupPathForRequest(request);
    return urlViewMapping.get(lookupPath);
  }
}
