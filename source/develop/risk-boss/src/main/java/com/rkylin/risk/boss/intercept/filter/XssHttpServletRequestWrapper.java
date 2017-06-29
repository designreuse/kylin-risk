package com.rkylin.risk.boss.intercept.filter;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @company: rkylin
 * @author: tongzhu.yu
 * @since: 15-3-5 上午11:34 version: 1.0
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
  public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
    super(servletRequest);
  }

  @Override
  public String[] getParameterValues(String parameter) {
    String[] values = super.getParameterValues(parameter);
    if (values == null) {
      return null;
    }
    int count = values.length;
    String[] encodedValues = new String[count];
    for (int i = 0; i < count; i++) {
      encodedValues[i] = cleanXSS(values[i]);
    }
    return encodedValues;
  }

  @Override
  public String getParameter(String parameter) {
    String value = super.getParameter(parameter);
    if (value == null) {
      return null;
    }
    return cleanXSS(value);
  }

  @Override
  public String getHeader(String name) {
    String value = super.getHeader(name);
    if (value == null) {
      return null;
    }
    return cleanXSS(value);
  }

  private String cleanXSS(String value) {
    String val = StringEscapeUtils.escapeXml10(value);
    //        value= StringEscapeUtils.escapeHtml4(value);
    //        value= HtmlUtils.htmlEscape(value, "UTF-8");
    return val;
  }
}
