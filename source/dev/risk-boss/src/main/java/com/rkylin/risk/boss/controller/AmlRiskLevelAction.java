package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.RiskLevelBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Risklevel;
import com.rkylin.risk.core.entity.Role;
import com.rkylin.risk.core.service.RiskLevelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 201508031790 on 2015/9/22.
 */

@Controller
@RequestMapping("amlriskLevel")
public class AmlRiskLevelAction {
  private static final String OPERTYPE = "operType";
  @Resource
  private RiskLevelService riskLevelService;

  //查询客户反洗钱风控风险等级
  @RequestMapping("customAmlRiskLevel")
  public ModelAndView toQueryAmlRiskLevelCustomer(HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    ModelAndView view = new ModelAndView("riskLevel/custAmlRiskLevelModify");

    //查询客户反洗钱风控风险等级
    List<Risklevel> levels = riskLevelService.queryByRiskAndCustType("01", "01");
    //设置操作类型
    String operType = getOperType(auth, levels == null ? null : levels.get(0));

    view.addObject(OPERTYPE, operType);
    view.addObject("levels", levels);
    return view;
  }

  //修改客户反洗钱风险等级
  @RequestMapping("updateAmlLevelCustom")
  public ModelAndView updateRiskLevelCustom(@ModelAttribute RiskLevelBean levels,
      @RequestParam String operType, HttpServletRequest request) {
    ModelAndView view = new ModelAndView("riskLevel/custAmlRiskLevelModify");
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    //提交修改或审核客户反洗钱风险等级
    List<Risklevel> levelM =
        riskLevelService.insertOrUpdateRiskGrade(levels.getLevels(), auth, operType, "01", "01");

    if (levelM != null) {
      //修改成功后操作类型为已提交或已审核
      view.addObject(OPERTYPE, "COMMIT".equals(operType) ? "COMMITTED" : "CHECKED");
      view.addObject("message", "success");
    } else {
      //修改失败操作类型不变
      view.addObject(OPERTYPE, operType);
      view.addObject("message", "error");
    }
    view.addObject("levels", riskLevelService.queryByRiskAndCustType("01", "01"));
    return view;
  }

  public String getOperType(Authorization auth, Risklevel level) {
    String operType = "";
    List<Role> list = auth.getRoles();
    for (Role role : list) {
      if (Constants.RISK_CHECK_USER.equals(role.getRolename()) && !StringUtils.isEmpty(
          level.getCheckscore())) {
        //用户角色是审核，待审核分数字段不为空，类型为审核操作
        operType = "CHECK";
        break;
      } else if (Constants.RISK_CHECK_USER.equals(role.getRolename()) && StringUtils.isEmpty(
          level.getCheckscore())) {
        //用户角色是审核，待审核分数字段为空，类型为已审核完成
        operType = "CHECKED";
        break;
      } else if (Constants.RISK_OPER.equals(role.getRolename()) && StringUtils.isEmpty(
          level.getCheckscore())) {
        //用户角色是操作人，待审核分数字段为空，类型为提交
        operType = "COMMIT";
        break;
      } else if (Constants.RISK_OPER.equals(role.getRolename()) && !StringUtils.isEmpty(
          level.getCheckscore())) {
        //用户角色是操作人，待审核分数字段不为空，类型为已提交完成
        operType = "COMMITTED";
        break;
      }
    }
    if ("CHECK".equals(operType) && level != null && StringUtils.isEmpty(level.getCheckscore())) {
      operType = "OTHER";
    }
    return operType;
  }
}
