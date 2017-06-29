package com.rkylin.risk.boss.tag;

import com.rkylin.risk.boss.util.ApplicationFactory;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.service.DictionaryService;
import java.util.Map;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import java.util.List;

@Slf4j
@Setter
public class SelectTag extends DictTag {

  boolean nullOption = false;
  boolean multiple = false;
  String id;
  String value;
  String escapeValue;
  String nullLabel = null;
  String dictcode;
  String readonly;
  String onchange;
  String cssClass;

  private transient DictionaryService dictionaryService;

  public SelectTag() {
    dictionaryService = ApplicationFactory.getBean("dictionaryService");
  }

  @Override
  public int doStartTag() throws JspException {
    return SKIP_BODY;
  }

  @Override
  public int doEndTag() throws JspException {
    try {
      StringBuffer tagContent = new StringBuffer();

      createSelectHead(tagContent);

      createTitleOption(tagContent);

      createValueOptions(tagContent);

      createSelectEnd(tagContent);

      pageContext.getOut().write(tagContent.toString());
    } catch (Exception e) {
      log.info(e.getMessage(), e);
      throw new JspTagException(" write select tag error ", e);
    }
    return EVAL_PAGE;
  }

  public void createSelectHead(StringBuffer tagContent) {
    tagContent.append("<select");
    if (StringUtils.isNotEmpty(id)) {
      tagContent.append(" id =\"" + id + "\"");
    }
    if (StringUtils.isNotEmpty(name)) {
      tagContent.append(" name =\"" + name + "\"");
    }
    if (StringUtils.isNotEmpty(readonly)) {
      tagContent.append(" readonly =\"" + readonly + "\"");
    }
    if (disabled) {
      tagContent.append(" disabled=\"" + disabled + "\"");
    }
    if (multiple) {
      tagContent.append(" multiple=\"" + multiple + "\"");
    }
    if (StringUtils.isNotEmpty(cssClass)) {
      tagContent.append(" class=\"" + cssClass + "\"");
    }
    if (StringUtils.isNotEmpty(onchange)) {
      tagContent.append(" onchange=\"" + onchange + "\"");
    }
    if (StringUtils.isNotEmpty(onclick)) {
      tagContent.append(" onclick=\"" + onclick + "\"");
    }
    if (StringUtils.isNotEmpty(onfocus)) {
      tagContent.append(" onfocus=\"" + onfocus + "\"");
    }
    if (StringUtils.isNotEmpty(onmouseout)) {
      tagContent.append(" onmouseout=\"" + onmouseout + "\"");
    }
    if (StringUtils.isNotEmpty(onmouseover)) {
      tagContent.append(" onmouseover=\"" + onmouseover + "\"");
    }

    Map<String, Object> mapAttribute= super.getDynamicAttributes();
    for(Map.Entry<String,Object> entry:mapAttribute.entrySet()){
      tagContent.append(entry.getKey()+"=\"" + entry.getValue() + "\"");
    }
    tagContent.append(">\r\n");
  }

  protected void createSingleOption(StringBuffer tagContent, String value, String label,
      boolean selected) {
    tagContent.append("\t\t<option value=\"");
    tagContent.append(value);
    tagContent.append("\"");
    if (selected) {
      tagContent.append(" selected ");
    }
    tagContent.append(">");
    tagContent.append(StringUtils.defaultString(label));
    tagContent.append("</option>\r\n");
  }

  protected void createTitleOption(StringBuffer tagContent) {
    if (nullOption) {
      createSingleOption(tagContent, "", nullLabel, false);
    }
  }

  protected void createValueOptions(StringBuffer tagContent) {

    DictionaryCode dictionaryCode = new DictionaryCode();
    dictionaryCode.setDictcode(dictcode);
    List<DictionaryCode> dicts = dictionaryService.queryAllDict(dictionaryCode);

    if (dicts == null || dicts.size() == 0) {
      return;
    }
    /*createSingleOption(tagContent,"","请选择",false);*/
    for (DictionaryCode dict : dicts) {
      if (escapeValue != null && escapeValue.contains(dict.getCode())) {
        continue;
      } else if (dict.getCode().equals(value)) {
        createSingleOption(tagContent, dict.getCode(), dict.getName(), true);
      } else {
        createSingleOption(tagContent, dict.getCode(), dict.getName(), false);
      }
    }
  }

  protected void createSelectEnd(StringBuffer tagContent) {
    tagContent.append("</select>");
  }
}
