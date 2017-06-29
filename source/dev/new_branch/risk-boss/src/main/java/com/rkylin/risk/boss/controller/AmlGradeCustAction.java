package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.RiskGradeCust;
import com.rkylin.risk.core.entity.Role;
import com.rkylin.risk.core.service.CustomerinfoService;
import com.rkylin.risk.core.service.RiskGradeCustService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 201508031790 on 2015/9/21.
 */

@Controller
@RequestMapping("amlGradeCust")
@Slf4j
public class AmlGradeCustAction {

  @Resource
  private RiskGradeCustService riskTotalCustomerService;
  @Resource
  private CustomerinfoService customerinfoService;

  @RequestMapping("toQueryAmlRiskGradeCust")
  public ModelAndView toQueryRiskGradeCust(HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    ModelAndView view = new ModelAndView("riskGradeCust/amlRiskGradeQuery");
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

  @RequestMapping("toAjaxGetAmlRiskGradeDetail")
  private ModelAndView getPayRiskGradeDetail(@RequestParam String id,
      @RequestParam String operType) {
    ModelAndView view = new ModelAndView("riskGradeCust/amlRiskGradeDetail");
    //客户风险等级信息
    RiskGradeCust grade = riskTotalCustomerService.queryById(Integer.valueOf(id));
    //客户信息
    Customerinfo cust = customerinfoService.queryOne(String.valueOf(grade.getCustid()));
    view.addObject("grade", grade);
    view.addObject("cust", cust);
    view.addObject("operType", operType);
    return view;
  }
}
