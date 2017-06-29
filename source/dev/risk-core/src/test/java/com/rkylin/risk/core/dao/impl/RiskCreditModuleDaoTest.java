package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CreditModuleDao;
import com.rkylin.risk.core.entity.CreditModule;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author qiuxian
 * @create 2016-10-09 10:27
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring-test.xml"})
public class RiskCreditModuleDaoTest {

  @Resource
  private CreditModuleDao creditModuleDao;

  @Test
  public void query() {
    creditModuleDao.query("1", true);
  }

  @Test
  public void insertTest() {
    CreditModule creditModule = new CreditModule();
    creditModule.setAtomicQuery(true);
    creditModule.setModuleName("123");
    creditModule.setModuleQueryName("123");
    creditModule = creditModuleDao.insert(creditModule);
    assertThat(creditModule).isNotNull();
  }
}
