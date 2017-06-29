package com.rkylin.risk.boss.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/28 version: 1.0
 */
public class FormateDateTag extends TagSupport {
  @Setter
  private Object value;
  @Setter
  private String pattern;

  private static final String DEFAULT_INSTANT_PATTERN = "yyyy-MM-dd HH:mm:ss";
  private static final String DEFAULT_PARTIAL_PATTERN = "yyyy-MM-dd";

  @Override
  public int doEndTag() throws JspException {

    if (value == null) {
      return EVAL_PAGE;
    }

    String formatted = null;
    if (value instanceof DateTime) {
      if (StringUtils.isBlank(pattern)) {
        pattern = DEFAULT_INSTANT_PATTERN;
      }
      DateTime instant = (DateTime) value;
      formatted = DateTimeFormat.forPattern(pattern).print(instant);
    } else if (value instanceof LocalDate) {
      if (StringUtils.isBlank(pattern)) {
        pattern = DEFAULT_PARTIAL_PATTERN;
      }
      LocalDate partial = (LocalDate) value;
      formatted = DateTimeFormat.forPattern(pattern).print(partial);
    } else {
      throw new JspTagException("no support type");
    }
    try {
      pageContext.getOut().print(formatted);
    } catch (Exception ioe) {
      throw new JspTagException(ioe.toString(), ioe);
    }
    return EVAL_PAGE;
  }

  public void release() {
    value = null;
    pattern = null;
  }
}
