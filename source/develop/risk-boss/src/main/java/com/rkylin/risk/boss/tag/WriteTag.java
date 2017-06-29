package com.rkylin.risk.boss.tag;

import com.rkylin.risk.boss.util.ApplicationFactory;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.service.DictionaryService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import java.util.List;

@Getter
@Setter
public class WriteTag extends DictTag {
  String dictcode;
  String code;

  private DictionaryService dictionaryService;

  public WriteTag() {
    dictionaryService = ApplicationFactory.getBean("dictionaryService");
  }

  @Override
  public int doEndTag() throws JspException {
    try {
      String labelName = getLabelName(dictcode, code);
      if (labelName == null) {
        labelName = "";
      }
      pageContext.getOut().write(labelName);
    } catch (Exception e) {
      throw new JspTagException("create write tag error ", e);
    }
    return EVAL_PAGE;
  }

  public String getLabelName(String keyName, String code) {
    String label = null;
    DictionaryCode dictionaryCode = new DictionaryCode();
    dictionaryCode.setDictcode(keyName);
    dictionaryCode.setCode(code);
    List<DictionaryCode> dicts = dictionaryService.queryAllDict(dictionaryCode);
    if (dicts == null) {
      return null;
    }

    for (DictionaryCode dict : dicts) {
      if (dict.getCode().equals(code)) {
        label = dict.getName();
        break;
      }
    }
    return StringUtils.defaultString(label);
  }
}
