package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.kie.spring.factorybeans.KieContainerSession;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.biz.LogicRuleCalBiz;
import javax.annotation.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by lina on 2016-7-26.
 */
@Component("logicRuleCalBiz")
public class LogicRuleCalBizImpl implements LogicRuleCalBiz {
  @Resource
  KieContainerSession kieContainerSession;

  @Value("${maven.groupPath}")
  private String groupPath;

  /**
   * 个人系统准入要求审核规则 - 帮帮助学
   */
  @Override
  public ResultBean calPersionalRuleByBestudent(LogicRuleBean logic) {
    return fireLogicRule(logic, groupPath + RuleConstants.BANGBANG_PERSON_KIE_CONTAINER,
        RuleConstants.BANGBANG_PERSON_RULE);
  }

  /**
   * 个人系统准入要求审核规则 - 课栈网
   */
  @Override
  public ResultBean calPersionalRuleByKezhanwang(LogicRuleBean logic) {
    return fireLogicRule(logic, groupPath + RuleConstants.KEZHAN_PERSON_KIE_CONTAINER,
        RuleConstants.KEZHAN_PERSON_RULE);
  }

  /**
   * 机构数据系统监测规则
   */
  @Override
  public ResultBean calOrganLogicRuleKezhanwang(LogicRuleBean logic) {
    return fireLogicRule(logic, groupPath + RuleConstants.KEZHAN_ORDER_KIE_CONTAINER,
        RuleConstants.KEZHAN_ORDER_RULE);
  }

  @Override
  public ResultBean calOrganLogicRuleBestudent(LogicRuleBean logic) {
    return fireLogicRule(logic, groupPath + RuleConstants.BANGBANG_ORDER_KIE_CONTAINER,
        RuleConstants.BANGBANG_ORDER_RULE);
  }

  @Override public ResultBean calGroupHeadLogicRule(LogicRuleBean logic) {
    return fireLogicRule(logic, groupPath + "P000008:P00000822", "P00000822");
  }

  @Override public ResultBean calGroupBranchLogicRule(LogicRuleBean logic) {
    return fireLogicRule(logic, groupPath + "P000008:P00000823", "P00000823");
  }

  @Override public ResultBean calLargeHeadLogicRule(LogicRuleBean logic) {
    return fireLogicRule(logic, groupPath + "P000008:P00000824", "P00000824");
  }

  @Override public ResultBean calLargeBranchLogicRule(LogicRuleBean logic) {
    return fireLogicRule(logic, groupPath + "P000008:P00000825", "P00000825");
  }

  @Override public ResultBean calmediumRule(LogicRuleBean logic) {
    return fireLogicRule(logic, groupPath + "P000008:P00000826", "P00000826");
  }

  private ResultBean fireLogicRule(LogicRuleBean logic, String kieContainerName,
      String kieSessionName) {
    ResultBean score = new ResultBean();
    KieContainer kieContainer =
        kieContainerSession.getBean(kieContainerName);
    KieSession kieSession = kieContainer.newKieSession(kieSessionName);
    FactHandle logicHandle = kieSession.insert(logic);
    FactHandle scoreHandle = kieSession.insert(score);
    kieSession.fireAllRules();
    kieSession.delete(logicHandle);
    kieSession.delete(scoreHandle);
    kieSession.destroy();
    return score;
  }
}
