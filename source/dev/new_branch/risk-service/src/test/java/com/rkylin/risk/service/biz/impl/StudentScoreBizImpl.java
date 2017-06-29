package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.kie.spring.factorybeans.KieContainerSession;
import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.dto.ResultBean;
import javax.annotation.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by lina on 2016-8-3.
 */

public class StudentScoreBizImpl implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Resource
  KieContainerSession kieContainerSession;

  public ResultBean calStudentScoreRule(LogicRuleBean logic) {
    ResultBean score = new ResultBean();
    KieContainer kieContainer =
        kieContainerSession.getBean("com.risk.rule.develop.P000008:P00000514");
    KieSession kieSession = kieContainer.newKieSession("P00000514");
    FactHandle logicHandle = kieSession.insert(logic);
    FactHandle scoreHandle = kieSession.insert(score);
    kieSession.fireAllRules();
    kieSession.delete(logicHandle);
    kieSession.delete(scoreHandle);
    kieSession.destroy();
    return score;
  }

  @Override public void setApplicationContext(ApplicationContext applicationContext)
      throws BeansException {
    this.applicationContext = applicationContext;
  }
}
