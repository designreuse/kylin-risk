package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.service.biz.LogicRuleCalBiz;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lina on 2016-6-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class LogicRuleCalBizImplTest {

  @Resource
  private LogicRuleCalBiz logicRuleCalBiz;

  @Test
  public void calLogicRuleTest() {
    LogicRuleBean logicRuleBean = new LogicRuleBean();
    logicRuleBean.setAge(null);
    //logicRuleBean.setIsCardNum("");
    logicRuleBean.setDegree("");
    logicRuleBean.setIsCourseName("");
    logicRuleBean.setCoursePriceRate(new Amount("1.2"));
  }

  @Test
  public void calOrganLogicRuleTest() {
    LogicRuleBean logicRuleBean = new LogicRuleBean();
    logicRuleBean.setDayNumRate(new Amount("5"));
    logicRuleBean.setDayNumRate1(new Amount("3"));
    logicRuleBean.setDayNumRate2(new Amount("10"));
    logicRuleBean.setMonthPayRate(new Amount("5"));
    logicRuleBean.setCoursePriceRate(new Amount());
  }
}
