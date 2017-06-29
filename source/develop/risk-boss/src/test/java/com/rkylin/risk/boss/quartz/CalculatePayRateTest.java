
package com.rkylin.risk.boss.quartz;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lina on 2016-8-19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class CalculatePayRateTest {

  @Resource
  private CalculatePayRate calculatePayRate;

  @Test
  public void calculatePayRateTest() {
    calculatePayRate.calculatePayRate();
  }
}
