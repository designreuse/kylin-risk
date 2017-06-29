package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.dto.FactorRuleBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.entity.FactorTempl;
import com.rkylin.risk.core.entity.Role;
import com.rkylin.risk.core.entity.Rule;
import com.rkylin.risk.core.service.DictionaryService;
import com.rkylin.risk.core.service.FactorTemplService;
import com.rkylin.risk.core.service.RuleService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lina on 2016-4-25.
 */
@RequestMapping("rule")
@Controller
public class RuleAction {
  @Resource
  private DictionaryService dictionaryService;
  @Resource
  private RuleService ruleService;
  @Resource
  private FactorTemplService factorTemplService;

  @RequestMapping("toQueryRule")
  public ModelAndView toQueryRule(HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String rolename = getRuleManageRole(auth);
    ModelAndView view = new ModelAndView("rule/ruleQuery");
    view.addObject("rolename", rolename);
    return view;
  }

  /**
   * 获取操作员管理规则权限
   *
   * @return “MODIFIER”:规则管理员   “SUPERMODIFIER”:超级规则管理员
   */
  public static String getRuleManageRole(Authorization auth) {
    String rolename = "";
    if (auth.getRoles() != null) {
      for (Object role : auth.getRoles()) {
        Role roletem = (Role) role;
        if ("规则管理人员".equals(roletem.getRolename())) {
          rolename = "MODIFIER";
          break;
        }
      }
    }
    if ("MODIFIER".equals(rolename) && "-1".equals(auth.getProducts()[0])) {
      rolename = "SUPERMODIFIER";
    }
    return rolename;
  }

  @RequestMapping("toAjaxAddRule")
  public ModelAndView toAddRule(HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    ModelAndView view = new ModelAndView("rule/ruleAdd");
    String rolename = getRuleManageRole(auth);
    view.addObject("rolename", rolename);
    return view;
  }

  @RequestMapping("toAjaxQueryRuleDetail")
  public ModelAndView getRuleDetail(@RequestParam String id, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    ModelAndView view = new ModelAndView("rule/ruleModify");
    Short ruleid = Short.parseShort(id);
    Rule rule = ruleService.queryById(ruleid);
    String rolename = getRuleManageRole(auth);
    List<FactorTempl> templs = factorTemplService.queryByGroup(rule.getGroupid());
    List<DictionaryCode> codes = dictionaryService.queryByDictCode("ruleparam");
    List<FactorRuleBean> factors = jointFactors(rule);
    view.addObject("templs", templs);
    view.addObject("rule", rule);
    view.addObject("rolename", rolename);
    view.addObject("ruleparams", codes);
    view.addObject("factors", factors);
    return view;
  }

  public List<FactorRuleBean> jointFactors(Rule rule) {
    List<FactorRuleBean> factors = new ArrayList<>();
    if (!StringUtils.isEmpty(rule.getFields())) {
      String[] cons = makeUpString(rule.getConditions());
      String[] conval = makeUpString(rule.getConditionvals());
      String[] logic = makeUpString(rule.getLogicsym());
      String[] field = rule.getFields().split(",");
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
      return null;
    }
  }
}
