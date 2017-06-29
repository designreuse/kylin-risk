package com.rkylin.risk.credit.service;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tomalloc on 16-7-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:risk-credit-unionpayadvisors-test.xml"})
public class UnionPayAdvisorsApiTest {
  @Resource
  private UnionPayAdvisorsApi unionPayAdvisorsApi;

  @Test
  public void requestTest() {
    CreditRequestParam param = new CreditRequestParam();
    param.setBankCard("6214830106765935");
    try {
      unionPayAdvisorsApi.request(param);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
