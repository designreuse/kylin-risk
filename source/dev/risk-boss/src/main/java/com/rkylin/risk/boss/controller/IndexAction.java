package com.rkylin.risk.boss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @company: rkylin
 * @author: tongzhu.yu
 * @since: 15-1-21 下午5:00 version: 1.0
 */
@Controller
public class IndexAction {

  @RequestMapping("/**/*.action")
  public ModelAndView head(HttpServletRequest request) {
    String prefix = request.getContextPath();
    if ("/".equals(prefix)) {
      prefix = "";
    }
    int begin = request.getRequestURI().indexOf(prefix);
    if (begin != -1) {
      begin += prefix.length();
    } else {
      begin = 0;
    }
    String jsp =
        request.getRequestURI().substring(begin, request.getRequestURI().lastIndexOf(".action"));
    return new ModelAndView(jsp);
  }
}
