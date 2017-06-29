package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.entity.Riskrule;
import com.rkylin.risk.core.service.CustomerinfoService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.core.service.RiskruleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201507270241 on 2015/9/17. 反洗钱可疑交易
 */
@Controller
@RequestMapping("amlDubiousTxn")
public class AmlDubiousTxnAction {
  private static final String RISKRULE = "riskrules";
  @Resource
  private OrderService orderService;
  @Resource
  private CustomerinfoService customerinfoService;
  @Resource
  private RiskruleService riskruleService;

  /**
   * 反洗钱可疑交易查询
   */
  @RequestMapping("toQueryAmlDubiousTxn")
  public ModelAndView toQueryAmlDubiousTxn() {
    List<Riskrule> riskrules = riskruleService.queryAll(new Riskrule());
    ModelAndView view = new ModelAndView("amlDubiousTxn/amlDubTxnQuery");
    view.addObject(RISKRULE, riskrules);
    return view;
  }

  @RequestMapping("toAjaxQueryDetailInfo")
  public ModelAndView getDetailInfo(@RequestParam Integer dubioustxnid, String customerid) {
    Customerinfo customers = customerinfoService.queryOne(customerid);
    List<Order> simpleBills = orderService.queryByAmlDub(dubioustxnid);
    ModelAndView view = new ModelAndView("amlDubiousTxn/amlDubTxnDetail");
    view.addObject("simpleBills", simpleBills);
    view.addObject("customers", customers);
    return view;
  }

  @RequestMapping("toAjaxQueryRuleInfo")
  public ModelAndView getRuleInfo(@RequestParam Integer id) {
    ModelAndView view = new ModelAndView("amlDubiousTxn/ruleInfo");
    Riskrule riskrule = riskruleService.queryOne(id);
    view.addObject("riskrule", riskrule);
    return view;
  }

  @RequestMapping("toAjaxAddAmlDubTxn")
  public ModelAndView toAddAmlDubTxn() {
    List<Riskrule> riskrules = riskruleService.queryAll(new Riskrule());
    ModelAndView view = new ModelAndView("amlDubiousTxn/amlDubTxnAdd");
    view.addObject(RISKRULE, riskrules);
    return view;
  }

  @RequestMapping("toAjaxGetCustomInfo")
  public ModelAndView getCustomInfo(@RequestParam String customnum) {
    Customerinfo customers = customerinfoService.queryOne(customnum);
    List<Order> simpleBills = orderService.queryByCusnum(customnum);
    List<Riskrule> riskrules = riskruleService.queryAll(new Riskrule());
    ModelAndView view = new ModelAndView("amlDubiousTxn/amlDubTxnAdd");
    view.addObject("customers", customers);
    view.addObject("simpleBills", simpleBills);
    view.addObject(RISKRULE, riskrules);
    return view;
  }
}
