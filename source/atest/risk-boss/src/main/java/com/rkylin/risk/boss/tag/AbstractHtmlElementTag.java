package com.rkylin.risk.boss.tag;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import java.util.Map;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-1-27 下午2:19 version: 1.0
 */
public abstract class AbstractHtmlElementTag extends BaseTag implements DynamicAttributes {

  public static final String ID_ATTRIBUTE = "id";
  public static final String NAME_ATTRIBUTE = "name";
  public static final String CLASS_ATTRIBUTE = "class";

  public static final String STYLE_ATTRIBUTE = "style";

  public static final String TITLE_ATTRIBUTE = "title";

  public static final String DIR_ATTRIBUTE = "dir";

  public static final String TABINDEX_ATTRIBUTE = "tabindex";

  public static final String ONCLICK_ATTRIBUTE = "onclick";

  public static final String ONDBLCLICK_ATTRIBUTE = "ondblclick";

  public static final String ONMOUSEDOWN_ATTRIBUTE = "onmousedown";

  public static final String ONMOUSEUP_ATTRIBUTE = "onmouseup";

  public static final String ONMOUSEOVER_ATTRIBUTE = "onmouseover";

  public static final String ONMOUSEMOVE_ATTRIBUTE = "onmousemove";

  public static final String ONMOUSEOUT_ATTRIBUTE = "onmouseout";

  public static final String ONKEYPRESS_ATTRIBUTE = "onkeypress";

  public static final String ONKEYUP_ATTRIBUTE = "onkeyup";

  public static final String ONKEYDOWN_ATTRIBUTE = "onkeydown";

  @Setter
  @Getter
  private String id;

  @Setter
  @Getter
  private String name;

  @Setter
  @Getter
  private String cssClass;

  @Setter
  @Getter
  private String cssStyle;

  @Setter
  @Getter
  private String title;

  @Setter
  @Getter
  private String dir;

  @Setter
  @Getter
  private String tabindex;

  @Setter
  @Getter
  private String onclick;

  @Setter
  @Getter
  private String ondblclick;

  @Setter
  @Getter
  private String onmousedown;

  @Setter
  @Getter
  private String onmouseup;

  @Setter
  @Getter
  private String onmouseover;

  @Setter
  @Getter
  private String onmousemove;

  @Setter
  @Getter
  private String onmouseout;

  @Setter
  @Getter
  private String onkeypress;

  @Setter
  @Getter
  private String onkeyup;

  @Setter
  @Getter
  private String onkeydown;

  @Getter
  private Map<String, Object> dynamicAttributes;

  @Getter
  @Setter
  private String customAttribute;

  @Override
  public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
    if (dynamicAttributes == null) {
      dynamicAttributes = Maps.newHashMap();
    }
    dynamicAttributes.put(localName, value);
  }

  protected void writeOptionalAttributes(HTMLTagWriter tagWriter) throws JspException {
    tagWriter.writeOptionalAttributeValue(ID_ATTRIBUTE, getId());
    tagWriter.writeOptionalAttributeValue(NAME_ATTRIBUTE, getName());
    tagWriter.writeOptionalAttributeValue(CLASS_ATTRIBUTE, getCssClass());
    tagWriter.writeOptionalAttributeValue(STYLE_ATTRIBUTE, getCssStyle());
    writeOptionalAttribute(tagWriter, TITLE_ATTRIBUTE, getTitle());
    writeOptionalAttribute(tagWriter, DIR_ATTRIBUTE, getDir());
    writeOptionalAttribute(tagWriter, TABINDEX_ATTRIBUTE, getTabindex());
    writeOptionalAttribute(tagWriter, ONCLICK_ATTRIBUTE, getOnclick());
    writeOptionalAttribute(tagWriter, ONDBLCLICK_ATTRIBUTE, getOndblclick());
    writeOptionalAttribute(tagWriter, ONMOUSEDOWN_ATTRIBUTE, getOnmousedown());
    writeOptionalAttribute(tagWriter, ONMOUSEUP_ATTRIBUTE, getOnmouseup());
    writeOptionalAttribute(tagWriter, ONMOUSEOVER_ATTRIBUTE, getOnmouseover());
    writeOptionalAttribute(tagWriter, ONMOUSEMOVE_ATTRIBUTE, getOnmousemove());
    writeOptionalAttribute(tagWriter, ONMOUSEOUT_ATTRIBUTE, getOnmouseout());
    writeOptionalAttribute(tagWriter, ONKEYPRESS_ATTRIBUTE, getOnkeypress());
    writeOptionalAttribute(tagWriter, ONKEYUP_ATTRIBUTE, getOnkeyup());
    writeOptionalAttribute(tagWriter, ONKEYDOWN_ATTRIBUTE, getOnkeydown());

    if (!CollectionUtils.isEmpty(this.dynamicAttributes)) {
      for (Map.Entry<String, Object> entry : this.dynamicAttributes.entrySet()) {
        String attr = entry.getKey();
        tagWriter.writeOptionalAttributeValue(attr, displayString(entry.getValue()));
      }
    }
    if (customAttribute != null) {
      tagWriter.writeAttribute(customAttribute);
    }
  }
}
