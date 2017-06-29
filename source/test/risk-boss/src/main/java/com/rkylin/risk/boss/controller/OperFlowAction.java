package com.rkylin.risk.boss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wanglingzhi on 2016-9-14.
 */
@Controller
@RequestMapping("operFlow")
public class OperFlowAction {

  @RequestMapping("toQueryOperFlow")
  public ModelAndView toQueryOperFlow() {
    return new ModelAndView("operFlow/operFlowQuery");
  }


}
