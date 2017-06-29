package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.RiskGradeMerc;
import com.rkylin.risk.core.service.OperatorLogService;
import com.rkylin.risk.core.service.RiskGradeMercService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 201508031790 on 2015/9/21.
 */
@RestController
@RequestMapping("/api/1/amlGradeMerc")
public class AmlGradeMercRestAction {

  @Resource
  private RiskGradeMercService riskGradeMercService;
  @Resource
  private OperatorLogService operatorLogService;

  //设置商户反洗钱风险等级
  @RequestMapping(value = "updateMercAmlGrade", method = RequestMethod.POST)
  public RiskGradeMerc updateGrade(@RequestParam String ids, @RequestParam String updateGrade,
      @RequestParam String reason, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    if (riskGradeMercService.updateRiskGradeList(ids, updateGrade, reason, auth)) {
      operatorLogService.insert(auth.getUserId(), auth.getUsername(), "RiskGradeMerc", "更新");
      return new RiskGradeMerc();
    } else {
      return null;
    }
  }

  //审核商户反洗钱风险等级
  @RequestMapping(value = "updateMercAmlGradeStatus", method = RequestMethod.POST)
  public RiskGradeMerc updateGradeStrtus(@RequestParam String ids, @RequestParam String status,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    if (riskGradeMercService.updateRiskStatusList(ids, status, auth)) {
      operatorLogService.insert(auth.getUserId(), auth.getUsername(), "RiskGradeMerc", "审核");
      return new RiskGradeMerc();
    } else {
      return null;
    }
  }
}
