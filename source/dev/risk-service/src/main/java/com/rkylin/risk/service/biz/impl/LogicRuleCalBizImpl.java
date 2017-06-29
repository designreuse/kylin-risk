package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.entity.Group;
import com.rkylin.risk.kie.spring.factorybeans.KieContainerSession;
import com.rkylin.risk.service.biz.LogicRuleCalBiz;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by lina on 2016-7-26.
 */
@Slf4j
@Component("logicRuleCalBiz")
public class LogicRuleCalBizImpl implements LogicRuleCalBiz {
  @Resource
  KieContainerSession kieContainerSession;

  @Value("${maven.groupPath}")
  private String groupPath;

  @Override public ResultBean calLogicRule(LogicRuleBean logic, Group group) {
    String kieContainerName = group.getIssuegroupid()+":"+group.getIssueartifactid();
    String kieSessionName = group.getIssueartifactid();
    log.info("风控调用规则：{}",kieContainerName);
    return fireLogicRule(logic,kieContainerName,kieSessionName);
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
