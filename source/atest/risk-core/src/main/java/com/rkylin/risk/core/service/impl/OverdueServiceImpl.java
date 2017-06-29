package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.OverdueDao;
import com.rkylin.risk.core.service.OverdueService;
import javax.annotation.Resource;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Created by ChenFumin on 2016-8-30.
 */
@Service
public class OverdueServiceImpl implements OverdueService {

  @Resource
  private OverdueDao overdueRateDao;

  @Override
  public String queryOverdueRate(String merchantid) {
    LocalDate localDate = new LocalDate();
    String yesterday = localDate.minusDays(1).toString("yyyy-MM-dd");
    return overdueRateDao.queryOverdueRate(merchantid, yesterday);
  }
}
