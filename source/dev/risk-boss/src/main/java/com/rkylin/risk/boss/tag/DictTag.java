package com.rkylin.risk.boss.tag;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictTag extends TagSupport implements DynamicAttributes {
  protected String name;
  protected boolean disabled = false;
  protected String onclick;
  protected String onfocus;
  protected String onmouseover;
  protected String onmouseout;

  @Getter
  private Map<String, Object> dynamicAttributes = Maps.newHashMap();

  @Override public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
    dynamicAttributes.put(localName, value);
  }
}
