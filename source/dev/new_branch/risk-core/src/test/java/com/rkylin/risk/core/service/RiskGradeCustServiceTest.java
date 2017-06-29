package com.rkylin.risk.core.service;

import com.rkylin.risk.core.BaseTest;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Operator;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by 201508240185 on 2015/9/18.
 */
public class RiskGradeCustServiceTest extends BaseTest {
  @Resource
  private RiskGradeCustService riskGradeCustService;
  @Resource
  private OperatorService operatorService;

  @Test
  public void testUpdateGradeStatus() {
    Operator oper = operatorService.queryOperatorByUsername("admin");
    Authorization auth = new Authorization();

    auth.setUserId(oper.getId());
    auth.setUsername(oper.getUsername());
    auth.setRealname(oper.getRealname());
    riskGradeCustService.updateRiskStatusList("1", "AGREE", auth);
  }
}
