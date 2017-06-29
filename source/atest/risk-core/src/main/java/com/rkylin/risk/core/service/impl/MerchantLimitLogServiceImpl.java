package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.MerchantLimitLogDao;
import com.rkylin.risk.core.entity.MerchantLimitLog;
import com.rkylin.risk.core.service.MerchantLimitLogService;

import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Created by cuixiaofang on 2016-7-19.
 */
@Service("merchantLimitLogService")
public class MerchantLimitLogServiceImpl implements MerchantLimitLogService {

  @Resource
  private MerchantLimitLogDao merchantLimitLogDao;

  @Override
  public List<MerchantLimitLog> queryAll(MerchantLimitLog merchantLimitLog) {
    return Collections.emptyList();
  }

  @Override
  public void addMerchantLimitLogBatch(List<MerchantLimitLog> merchantLimitLogs) {
    merchantLimitLogDao.addMerchantLimitLogBatch(merchantLimitLogs);
  }

  @Override
  public List<MerchantLimitLog> queryServenRate(String merchantid, String dateTime) {
    return merchantLimitLogDao.queryServenRate(merchantid, dateTime);
  }

  @Override
  public MerchantLimitLog queryYestoday(String merchantid, LocalDate dateTime) {
    return merchantLimitLogDao.queryYestoday(merchantid, dateTime);
  }

  @Override public MerchantLimitLog queryByMerchantAndDate(String merchantid, LocalDate dateTime) {
    return merchantLimitLogDao.queryByMerchantAndDate(merchantid, dateTime);
  }

  @Override public void update(MerchantLimitLog merchantLimitLog) {
    merchantLimitLogDao.update(merchantLimitLog);
  }

  @Override
  public MerchantLimitLog insert(MerchantLimitLog merchantLimitLog) {
    return merchantLimitLogDao.insert(merchantLimitLog);
  }

  @Override public void updateForOrderSyncFail(MerchantLimitLog todayLog) {
    merchantLimitLogDao.updateForOrderSyncFail(todayLog);
  }

  @Override
  public String queryOverdueRate(String merchantid) {
    return merchantLimitLogDao.queryOverdueRate(merchantid);
  }
}
