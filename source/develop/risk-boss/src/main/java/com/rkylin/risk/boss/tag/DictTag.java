package com.rkylin.risk.boss.tag;

import javax.servlet.jsp.tagext.TagSupport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictTag extends TagSupport {
  protected String name;
  protected boolean disabled = false;
  protected String onclick;
  protected String onfocus;
  protected String onmouseover;
  protected String onmouseout;
}
