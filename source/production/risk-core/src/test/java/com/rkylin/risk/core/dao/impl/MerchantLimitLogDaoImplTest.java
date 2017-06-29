package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.BaseTest;
import com.rkylin.risk.core.dao.MerchantLimitLogDao;
import com.rkylin.risk.core.entity.MerchantLimitLog;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * Created by lina on 2016-8-19.
 */
public class MerchantLimitLogDaoImplTest extends BaseTest {
  @Resource
  private MerchantLimitLogDao merchantLimitLogDao;

  @Test
  public void queryServenRateTest() {
    List<MerchantLimitLog> logs = merchantLimitLogDao.queryServenRate("10049", "20161111");
    for (MerchantLimitLog log : logs) {
      System.out.println(log.toString()
      );
    }
  }
}
