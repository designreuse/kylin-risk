package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.Operator;
import com.rkylin.risk.core.entity.WarningSet;
import com.rkylin.risk.core.service.OperatorService;
import com.rkylin.risk.core.service.WarningSetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508031790 on 2015/9/6.
 */

@Controller
@RequestMapping("warningset")
public class WarningSetAction {

  @Resource
  private WarningSetService warningSetService;

  @Resource
  private OperatorService operatorService;

  @RequestMapping("toQueryWarningSet")
  public ModelAndView toWarningSetQuery() {
    return new ModelAndView("warningset/warningsetQuery");
  }

  @RequestMapping("toAddWarnSet")
  public ModelAndView toAddOperator() {
    ModelAndView modelAndView = new ModelAndView("warningset/warningsetAdd");
    List<Operator> list = operatorService.queryAllOperator(new Operator());
    modelAndView.addObject("operatorlist", list);
    return modelAndView;
  }

  @RequestMapping("towarningsetModify")
  public ModelAndView toModify(String wsId) {
    ModelAndView modelAndView = new ModelAndView("warningset/warningsetModify");
    WarningSet ws = warningSetService.queryById(wsId);
    modelAndView.addObject("warningset", ws);
    return modelAndView;
  }
}
