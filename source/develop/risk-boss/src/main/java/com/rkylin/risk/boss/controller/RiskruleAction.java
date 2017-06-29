package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.Riskrule;
import com.rkylin.risk.core.service.RiskruleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;

/**
 * Created by 201507270241 on 2015/9/6.
 */
@Controller
@RequestMapping("riskrule")
public class RiskruleAction {

  @Resource
  private RiskruleService riskruleService;

  /**
   * 查询规则跳转
   */
  @RequestMapping("toQueryRiskrule")
  public ModelAndView toRiskruleQuery(@ModelAttribute Riskrule riskrules) {
    ModelAndView view = new ModelAndView("riskRule/riskRuleQuery");
    view.addObject("riskrules", riskrules);
    return view;
  }

  /**
   * 查询详细信息
   */
  @RequestMapping("getRiskruleDetail")
  public ModelAndView queryDetail(@ModelAttribute Riskrule riskrules, @RequestParam Integer ids,
      String dealType) {
    Riskrule riskrule = riskruleService.queryOne(ids);
    ModelAndView view = new ModelAndView("riskRule/riskRuleDetail");
    view.addObject("riskrule", riskrule);
    //dealType  modify---->修改  query---->查询
    view.addObject("dealType", dealType);
    view.addObject("riskrules", riskrules);
    return view;
  }

  /**
   * 添加跳转
   */
  @RequestMapping("toAddRiskrule")
  public ModelAndView toInsert() {
    return new ModelAndView("riskRule/riskRuleAdd");
  }
}
