package com.rkylin.risk.boss.tag;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.jsp.JspException;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-1-27 下午2:31 version: 1.0
 */
public abstract class AbstractHtmlInputElementTag extends AbstractHtmlElementTag {
  /**
   * The name of the '{@code onfocus}' attribute.
   */
  public static final String ONFOCUS_ATTRIBUTE = "onfocus";

  /**
   * The name of the '{@code onblur}' attribute.
   */
  public static final String ONBLUR_ATTRIBUTE = "onblur";

  /**
   * The name of the '{@code onchange}' attribute.
   */
  public static final String ONCHANGE_ATTRIBUTE = "onchange";

  /**
   * The name of the '{@code accesskey}' attribute.
   */
  public static final String ACCESSKEY_ATTRIBUTE = "accesskey";

  /**
   * The name of the '{@code disabled}' attribute.
   */
  public static final String DISABLED_ATTRIBUTE = "disabled";

  /**
   * The name of the '{@code readonly}' attribute.
   */
  public static final String READONLY_ATTRIBUTE = "readonly";

  @Setter
  @Getter
  private String onfocus;

  @Setter
  @Getter
  private String onblur;

  @Setter
  @Getter
  private String onchange;

  @Setter
  @Getter
  private String accesskey;

  @Setter
  @Getter
  private boolean disabled;

  @Setter
  @Getter
  private boolean readonly;

  @Override
  protected void writeOptionalAttributes(HTMLTagWriter tagWriter) throws JspException {
    super.writeOptionalAttributes(tagWriter);
    writeOptionalAttribute(tagWriter, ONFOCUS_ATTRIBUTE, getOnfocus());
    writeOptionalAttribute(tagWriter, ONBLUR_ATTRIBUTE, getOnblur());
    writeOptionalAttribute(tagWriter, ONCHANGE_ATTRIBUTE, getOnchange());
    writeOptionalAttribute(tagWriter, ACCESSKEY_ATTRIBUTE, getAccesskey());
    if (isDisabled()) {
      tagWriter.writeAttribute(DISABLED_ATTRIBUTE, "disabled");
    }
    if (isReadonly()) {
      writeOptionalAttribute(tagWriter, READONLY_ATTRIBUTE, "readonly");
    }
  }
}
