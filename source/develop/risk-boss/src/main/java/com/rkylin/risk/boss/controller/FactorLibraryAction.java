package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.FactorLibrary;
import com.rkylin.risk.core.service.FactorLibraryService;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lina on 2016-7-28.
 */
@Controller
@RequestMapping("factorLibrary")
public class FactorLibraryAction {
  @Resource
  private FactorLibraryService factorLibraryService;

  @RequestMapping("toQueryFactorLibrary") ModelAndView toQueryFactorLibrary() {
    return new ModelAndView("factorLibrary/factorLibraryQuery");
  }

  @RequestMapping("toAddFactorLibrary") ModelAndView toAddFactorLibrary() {
    return new ModelAndView("factorLibrary/factorLibraryAdd");
  }

  @RequestMapping("queryFactorLibraryDetail") ModelAndView queryFactorLibraryDetail(
      @RequestParam String id) {
    ModelAndView view = new ModelAndView("factorLibrary/factorLibraryModify");
    FactorLibrary factorLibrary = null;
    if (!StringUtils.isEmpty(id)) {
      factorLibrary = factorLibraryService.queryById(Short.parseShort(id));
    }
    view.addObject("factorLibrary", factorLibrary);
    return view;
  }
}
