package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.CustomebwgList;
import com.rkylin.risk.core.service.CustomerbwgService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 201508031790 on 2015/9/7.
 */
@Controller
@RequestMapping("customerbwg")
public class CustomerBwgAction {
  @Resource private CustomerbwgService customerbwgService;

  @RequestMapping("toQueryCustomerbwg")
  public ModelAndView tocustomerbwgQuery(HttpServletRequest request) {
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
    ModelAndView mv = new ModelAndView("customebwglist/customebwglistQuery");
    mv.addObject("flag", flag);
    return mv;
  }

  @RequestMapping("_toModifyCustbwg")
  public ModelAndView toCustbwgModify(String id) {
    ModelAndView modelAndView = new ModelAndView("customebwglist/customerbwglistModify");
    CustomebwgList bwg = customerbwgService.queryById(Integer.valueOf(id));
    modelAndView.addObject("customebwglist", bwg);
    return modelAndView;
  }

  @RequestMapping("_toVerifyCustbwg")
  public ModelAndView toCustbwgVerify(String id) {
    ModelAndView modelAndView = new ModelAndView("customebwglist/customebwgVerify");
    CustomebwgList bwg = customerbwgService.queryById(Integer.valueOf(id));
    modelAndView.addObject("customebwglist", bwg);
    return modelAndView;
  }

  @RequestMapping("toQueryCusBWG")
  public ModelAndView toAddCustombwg(String type) {
    ModelAndView modelAndView = new ModelAndView("customebwglist/customebwglistAdd");
    modelAndView.addObject("type", type);
    return modelAndView;
  }

  @RequestMapping("_toAddCustomBwg")
  public ModelAndView toAddCustomBwgs(String ids, String type) {
    ModelAndView modelAndView = new ModelAndView("customebwglist/confirCustomerbwg");
    modelAndView.addObject("ids", ids);
    modelAndView.addObject("type", type);
    return modelAndView;
  }
}
