package com.rkylin.risk.boss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lina on 2016-7-21.
 */
@Controller
@RequestMapping("groupVersion")
public class GroupVersionAction {
  @RequestMapping("toQueryGroupVersion")
  public ModelAndView toQueryGroupVersion() {
    return new ModelAndView("groupVersion/groupVersionQuery");
  }
}
