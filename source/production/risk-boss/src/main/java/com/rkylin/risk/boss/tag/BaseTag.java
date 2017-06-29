package com.rkylin.risk.boss.tag;

import org.springframework.util.ObjectUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-1-27 下午2:14 version: 1.0
 */
public abstract class BaseTag extends TagSupport {
  protected HTMLTagWriter createTagWriter() {
    return new HTMLTagWriter(this.pageContext);
  }

  protected final void writeOptionalAttribute(HTMLTagWriter tagWriter, String attributeName,
      String value)
      throws JspException {
    if (value != null) {
      tagWriter.writeOptionalAttributeValue(attributeName, displayString(value));
    }
  }

  protected String displayString(Object value) {
    return ObjectUtils.getDisplayString(value);
  }

  @Override
  public final int doStartTag() throws JspException {
    return writeTagContent(createTagWriter());
  }

  public abstract int writeTagContent(HTMLTagWriter writer) throws JspException;

  @Override
  public int doEndTag() throws JspException {
    return super.doEndTag();
  }
}
