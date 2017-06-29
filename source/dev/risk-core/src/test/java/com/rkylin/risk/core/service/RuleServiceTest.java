package com.rkylin.risk.core.service;

import com.rkylin.risk.core.BaseTest;
import com.rkylin.risk.core.entity.Rule;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * Created by lina on 2016-6-2.
 */
public class RuleServiceTest extends BaseTest {
  @Resource
  private RuleService ruleService;

  @Test
  public void createRulecodeTest() {
    System.out.println(ruleService.createRulecode());
  }

  @Test
  public void queryRuleProByProTest() {
    List<Rule> rule = ruleService.queryRuleProByPro("P000011", "01");
  }
}
