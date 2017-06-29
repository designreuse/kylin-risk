package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.dto.FactorRuleBean;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.entity.FactorTempl;
import com.rkylin.risk.core.entity.GroupVersion;
import com.rkylin.risk.core.entity.RuleHis;
import com.rkylin.risk.core.service.DictionaryService;
import com.rkylin.risk.core.service.FactorTemplService;
import com.rkylin.risk.core.service.GroupVersionService;
import com.rkylin.risk.core.service.RuleHisService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lina on 2016-4-25.
 */
@RequestMapping("ruleHis")
@Controller
public class RuleHisAction {
  @Resource
  private DictionaryService dictionaryService;
  @Resource
  private RuleHisService ruleHisService;
  @Resource
  private FactorTemplService factorTemplService;
  @Resource
  private GroupVersionService groupVersionService;

  @RequestMapping("toAjaxQueryRuleHis")
  public ModelAndView toQueryRuleHis(@RequestParam String queryId,String queryType
  ) {
    ModelAndView view = new ModelAndView("ruleHis/ruleHisQuery");
    view.addObject("queryId", queryId);
    view.addObject("queryType", queryType);
   // view.addObject("restr", restr);
    return view;
  }

  @RequestMapping("toAjaxQueryRuleHisDetail")
  public ModelAndView toQueryRuleHisDetail(@RequestParam String id) {
    ModelAndView view = new ModelAndView("ruleHis/ruleHisDetailQuery");
    Short ruleid = Short.parseShort(id);
    RuleHis ruleHis = ruleHisService.queryById(ruleid);
    GroupVersion groupVersion = groupVersionService.queryById(ruleHis.getGroupversionid());
    List<FactorTempl> templs = factorTemplService.queryByGroup(groupVersion.getGroupid());
    List<DictionaryCode> codes = dictionaryService.queryByDictCode("ruleparam");
    List<FactorRuleBean> factors = jointFactors(ruleHis);
    view.addObject("templs", templs);
    view.addObject("factors", factors);
    view.addObject("rule", ruleHis);
    view.addObject("ruleid", id);
    view.addObject("ruleparams", codes);
    //view.addObject("ruleids", ruleids);
    //view.addObject("restr", restr);
    return view;
  }

  public List<FactorRuleBean> jointFactors(RuleHis ruleHis) {
    List<FactorRuleBean> factors = new ArrayList<>();
    if (!StringUtils.isEmpty(ruleHis.getFields())) {
      String[] cons = makeUpString(ruleHis.getConditions());
      String[] conval = makeUpString(ruleHis.getConditionvals());
      String[] logic = makeUpString(ruleHis.getLogicsym());
      String[] field = ruleHis.getFields().split(",");
      for (int i = 0; i < field.length; i++) {
        FactorRuleBean factor = new FactorRuleBean();
        if (field[i].startsWith("(")) {
          factor.setLeftbrac("(");
          field[i] = StringUtils.substringAfter(field[i], "(");
        }
        if (field[i].endsWith(")")) {
          factor.setRightbrac(")");
          field[i] = StringUtils.substringBefore(field[i], ")");
        }
        factor.setFields(field[i]);
        factor.setConditions(cons[i]);
        factor.setConditionvals(conval[i]);
        factor.setLogicsym(i > 0 ? logic[i - 1] : "");
        factors.add(factor);
      }
    }
    return factors;
  }

  public String[] makeUpString(String str) {
    if (str != null) {
      String strTem = str.endsWith(",") ? str + " " : str;
      return strTem.split(",");
    } else {
      return new String[0];
    }
  }
}
