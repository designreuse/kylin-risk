package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.PersonBiz;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lina on 2016-11-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class PersonBizImplTest {
  @Resource
  private PersonBiz personBiz;

  @Test
  public void orderStatusValidateTest(){
    PersonFactor personFactor = new PersonFactor();
    personFactor.setCertificatenumber("1239");
    personFactor.setFirstid("1123");
    System.out.println(personBiz.orderStatusValidate(personFactor));
  }
}
