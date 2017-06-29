package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Merchant;
import com.rkylin.risk.core.entity.RiskGradeMerc;
import com.rkylin.risk.core.entity.Role;
import com.rkylin.risk.core.service.MerchantService;
import com.rkylin.risk.core.service.RiskGradeMercService;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 201508240185 on 2015/9/16.
 */
@Controller
@RequestMapping("riskGradeMerc")
public class RiskGradeMercAction {
  @Resource
  private RiskGradeMercService riskGradeMercService;
  @Resource
  private MerchantService merchantService;

  @RequestMapping("toQueryPayRiskGradeMerc")
  public ModelAndView toQueryRiskGradeCust(HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    ModelAndView view = new ModelAndView("riskGradeMerc/payRiskGradeQuery");
    String operType = "";
    List<Role> list = auth.getRoles();
    for (Role role : list) {
      if (Constants.RISK_CHECK_USER.equals(role.getRolename())) {
        operType = "CHECK";
        break;
      } else if (Constants.RISK_OPER.equals(role.getRolename())) {
        operType = "COMMIT";
        break;
      }
    }
    view.addObject("operType", operType);
    return view;
  }

  @RequestMapping("getMercPayRiskGradeDetail")
  public ModelAndView getPayRiskGradeDetail(@RequestParam String id,
      @RequestParam String operType) {
    ModelAndView view = new ModelAndView("riskGradeMerc/payRiskGradeDetail");
    RiskGradeMerc grade = riskGradeMercService.queryById(Integer.parseInt(id));
    Merchant merc = merchantService.queryOne(grade.getMerchantid());
    view.addObject("grade", grade);
    view.addObject("merc", merc);
    view.addObject("operType", operType);
    return view;
  }
}
