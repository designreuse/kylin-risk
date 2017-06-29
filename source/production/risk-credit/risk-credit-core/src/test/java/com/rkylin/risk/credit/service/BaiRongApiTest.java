package com.rkylin.risk.credit.service;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-8-1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:risk-credit-bairong-test.xml"})
public class BaiRongApiTest {
  @Resource
  private BaiRongApi baiRongApi;

  @Test
  public void requestTest() {
    CreditRequestParam requestParam = new CreditRequestParam();
    requestParam.setName("胡国强");
    requestParam.setIdNumber("231181198903281530");
    requestParam.setMobile("18603605431");
    String str = baiRongApi.request(requestParam).toString();
    assertThat(str).isNotEmpty();
  }
}
