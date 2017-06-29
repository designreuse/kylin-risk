package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Riskrule;
import com.rkylin.risk.core.service.RiskruleService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 201507270241 on 2015/9/8.
 */
@RestController
@RequestMapping("/api/1/riskrule")
public class RiskruleRestAction {

  @Resource
  private RiskruleService riskruleService;

  /**
   * 添加规则
   */
  @RequestMapping(value = "addRiskrule")
  public Riskrule insert(@ModelAttribute Riskrule riskrule, HttpServletRequest request) {
    if (riskrule != null) {
      Authorization auth = (Authorization) request.getSession().getAttribute("auth");
      riskruleService.insert(riskrule, auth);
    }
    return riskrule;
  }

  /**
   * 更新规则
   */
  @RequestMapping(value = "updateRiskrule")
  public Riskrule update(@ModelAttribute Riskrule riskrule, HttpServletRequest request) {
    if (riskrule != null) {
      Authorization auth = (Authorization) request.getSession().getAttribute("auth");
      riskruleService.modify(riskrule, auth);
    }
    return new Riskrule();
  }

  /**
   * 更新规则状态
   */
  @RequestMapping("modifyRiskruleStatus")
  public Riskrule modifyRiskruleStatus(@RequestParam String ids, String setStatus,
      HttpServletRequest request) {
    if (!"".equals(ids)) {
      Authorization auth = (Authorization) request.getSession().getAttribute("auth");
      riskruleService.modifyStatus(ids, setStatus, auth);
    }
    return new Riskrule();
  }

  /**
   * 删除功能
   */
  @RequestMapping(value = "deleteRiskrule")
  public Riskrule delete(@RequestParam String ids, HttpServletRequest request) {
    if (!"".equals(ids)) {
      Authorization auth = (Authorization) request.getSession().getAttribute("auth");
      riskruleService.delete(ids, auth);
    }
    return new Riskrule();
  }
}
