package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.RiskGradeCust;
import com.rkylin.risk.core.entity.Role;
import com.rkylin.risk.core.service.AccountService;
import com.rkylin.risk.core.service.CustomerinfoService;
import com.rkylin.risk.core.service.RiskGradeCustService;
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
@RequestMapping("riskGradeCust")
public class RiskGradeCustAction {
  @Resource
  private RiskGradeCustService riskTotalCustomerService;
  @Resource
  private CustomerinfoService customerinfoService;
  @Resource
  private AccountService accountService;

  @RequestMapping("toQueryPayRiskGradeCust")
  public ModelAndView toQueryRiskGradeCust(HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    ModelAndView view = new ModelAndView("riskGradeCust/payRiskGradeQuery");
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

  @RequestMapping("getPayRiskGradeDetail")
  public ModelAndView getPayRiskGradeDetail(@RequestParam String id,
      @RequestParam String operType) {
    ModelAndView view = new ModelAndView("riskGradeCust/PayRiskGradeDetail");
    //客户风险等级信息
    RiskGradeCust grade = riskTotalCustomerService.queryById(Integer.parseInt(id));
    //客户信息
    Customerinfo cust = customerinfoService.queryOne(String.valueOf(grade.getCustid()));

    view.addObject("grade", grade);
    view.addObject("cust", cust);
    view.addObject("operType", operType);
    return view;
  }
}
