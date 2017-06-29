package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.FactorTempl;
import com.rkylin.risk.core.service.FactorTemplService;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lina on 2016-5-23.
 */
@Controller
@RequestMapping("factorTempl")
public class FactorTemplAction {
  @Resource
  private FactorTemplService factorTemplService;

  @RequestMapping("toQueryFactorTempl") ModelAndView toQueryFactorTempl() {
    return new ModelAndView("factorTempl/factorTemplQuery");
  }

  @RequestMapping("toAddFactorTempl") ModelAndView toAddFactorTempl() {
    return new ModelAndView("factorTempl/factorTemplAdd");
  }

  @RequestMapping("queryFactorTemplDetail") ModelAndView queryFactorTemplDetail(
      @RequestParam String id) {
    ModelAndView view = new ModelAndView("factorTempl/factorTemplModify");
    FactorTempl factorTempl = null;
    if (!StringUtils.isEmpty(id)) {
      factorTempl = factorTemplService.findById(Short.parseShort(id));
    }
    view.addObject("factorTempl", factorTempl);
    return view;
  }
}
