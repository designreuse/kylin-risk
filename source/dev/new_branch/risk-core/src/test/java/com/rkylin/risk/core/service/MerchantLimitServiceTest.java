package com.rkylin.risk.core.service;

import com.rkylin.risk.core.BaseTest;
import com.rkylin.risk.core.entity.MerchantLimit;
import java.util.concurrent.CountDownLatch;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * Created by lina on 2016-8-19.
 */
public class MerchantLimitServiceTest extends BaseTest {
  @Resource
  private MerchantLimitService merchantLimitService;

  @Test
  public void queryByUserrelateidTest() {
    final CountDownLatch countDownLatch = new CountDownLatch(50);
    for (int i = 0; i < 50; i++) {
      new Thread(new Runnable() {
        @Override public void run() {
          MerchantLimit merchantLimit =
              applicationContext.getBean("merchantLimitService", MerchantLimitService.class)
                  .queryByUserrelateid("10047", null);
          System.out.println(merchantLimit);
          countDownLatch.countDown();
        }
      }).start();
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
