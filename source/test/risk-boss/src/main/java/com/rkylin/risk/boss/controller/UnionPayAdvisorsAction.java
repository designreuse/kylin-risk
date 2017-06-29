package com.rkylin.risk.boss.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wjr on 2016-12-12. 银联智策Action
 */
@Controller
@RequestMapping("unionPayAdvisors")
@Slf4j
public class UnionPayAdvisorsAction {

  //跳转查询页面
  @RequestMapping("toQueryUnionPayAdvisors")
  public ModelAndView toQueryUnionPayAdvisors() {
    return new ModelAndView("unionPayAdvisors/unionPayAdvisorsQuery");
  }

}
