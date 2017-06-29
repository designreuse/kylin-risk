package com.rkylin.risk.service.api.impl;

import com.rkylin.risk.operation.api.MerchantCheckApi;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lina on 2016-8-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class MerchantCheckApiImplTest {
  @Resource
  private MerchantCheckApi merchantCheckApi;

  @Test
  public void checkMerchantMsgTest() {
    String resultStr = merchantCheckApi.checkMerchantMsg("4757");
    System.out.println(resultStr);
    assertThat(resultStr).isNotNull();
  }

  @Test
  public void calcMerchantMsgTest() {
    String resultStr = merchantCheckApi.calcMerchantMsg("4757");
    System.out.println(resultStr);
    assertThat(resultStr).isNotNull();
  }
}
