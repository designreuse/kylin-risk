package com.rkylin.risk.boss.tag;

import java.util.List;
import javax.servlet.jsp.JspException;
import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 */
@Getter
@Setter
public class RadioTag extends DictTag {

  String code;
  String dictcode;

  @Override
  public int doEndTag() throws JspException {
    //try {
    //  StringBuffer tagContent = new StringBuffer();
    //    List<Dictionary> dicts = DictUtil.getDictsByDictTypeId(dictcode);
    //
    //createRadios(tagContent,dicts);
    //  pageContext.getOut().write(tagContent.toString());
    //} catch (Exception e) {
    //  throw new JspTagException(" write select tag error ", e);
    //}
    return EVAL_PAGE;
  }

  public void createRadios(StringBuffer tagContent, List<Object> dicts) {
    //for(Dictionary dict:dicts){
    //  tagContent.append("<input type=\"radio\" value=\""+dict.getDictId()+"\"");
    //  if(StringUtils.isNotEmpty(name)){
    //  tagContent.append(" name=\""+name+"\"");
    //  }
    //  if(disabled == true){
    //    tagContent.append(" disabled=\"true\"");
    //  }
    //
    //  if(StringUtils.isNotEmpty(onclick)){
    //    tagContent.append(" onclick=\""+onclick+"\"");
    //  }
    //  if(StringUtils.isNotEmpty(onfocus)){
    //    tagContent.append(" onfocus=\""+onfocus+"\"");
    //  }
    //  if(StringUtils.isNotEmpty(onmouseover)) {
    //    tagContent.append(" onmouseover=\""+onmouseover+"\"");
    //  }
    //  if(StringUtils.isNotEmpty(onmouseout)) {
    //    tagContent.append(" onmouseout=\""+onmouseout+"\"");
    //  }
    //  if(dict.getDictId().equals(code)) {
    //    tagContent.append(" checked=\"true\"");
    //  }
    //
    //  tagContent.append(">&nbsp;&nbsp;").
    // append(dict.getDictName()).append("&nbsp;&nbsp;\r\n");
    //}
  }
}
