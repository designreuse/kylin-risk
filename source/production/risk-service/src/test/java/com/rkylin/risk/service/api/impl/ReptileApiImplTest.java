package com.rkylin.risk.service.api.impl;

import com.rkylin.risk.operation.api.ReptileApi;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lina on 2016-8-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class ReptileApiImplTest {
  @Resource
  private ReptileApi reptileApi;

  @Test
  public void reptileInfoTest() {
    String str = reptileApi.reptileInfo("3788", "2", "a2cf5702e7e706fd8b9ff309dcbe94b8");
    System.out.println(str);
  }

  @Test
  public void reptileDetailTest() {
    List<String> details =
        reptileApi.reptileDetail("3788", "2", "6", "a2cf5702e7e706fd8b9ff309dcbe94b8");
    for (String str : details) {
      System.out.println(str);
    }
  }
}
