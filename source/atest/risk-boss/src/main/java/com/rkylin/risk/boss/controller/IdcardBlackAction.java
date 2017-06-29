package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 201508031790 on 2015/9/19.
 */
@Controller
@RequestMapping("idcardblack")
public class IdcardBlackAction {

  @RequestMapping("toQueryIdBlack")
  public ModelAndView toidcardbQuery(HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String flag = "03";
    for (int i = 0; i < auth.getRoles().size(); i++) {
      if (auth.getRoles().get(i).toString().contains(Constants.RISK_CHECK_USER)) {
        flag = "01";
        break;
      } else if (auth.getRoles().get(i).toString().contains(Constants.RISK_OPER)) {
        flag = "02";
        break;
      }
    }
    ModelAndView mv = new ModelAndView("idcardblacklist/idcardblacklistQuery");
    mv.addObject("flag", flag);
    return mv;
  }

  @RequestMapping("_toImportBlack")
  public ModelAndView toImportbwg(String type) {
    ModelAndView modelAndView = new ModelAndView("idcardblacklist/idcardblackImport");
    modelAndView.addObject("type", type);
    return modelAndView;
  }
}
