package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.MerchantLimitLogDao;
import com.rkylin.risk.core.entity.MerchantLimitLog;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Repository;

/**
 * Created by cuixiaofang on 2016-7-19.
 */
@Repository("merchantLimitLogDao")
public class MerchantLimitLogDaoImpl extends BaseDaoImpl<MerchantLimitLog>
    implements MerchantLimitLogDao {

  @Override
  public List<MerchantLimitLog> queryAll(MerchantLimitLog merchantLimitLog) {
    return null;
  }

  @Override
  public void addMerchantLimitLogBatch(List<MerchantLimitLog> merchantLimitLogs) {
    super.addBatch("addMerchantLimitLogBatch", merchantLimitLogs);
  }

  @Override
  public List<MerchantLimitLog> queryServenRate(String merchantid, String dateTime) {
    LocalDate fromDate = LocalDate.parse(dateTime, DateTimeFormat.forPattern("yyyyMMdd"));
    LocalDate toDate = fromDate.minusDays(7);
    Map map = new HashMap();
    map.put("merchantid", merchantid);
    map.put("fromDate", fromDate);
    map.put("toDate", toDate);
    return super.queryList("queryServenRate", map);
  }

  @Override
  public MerchantLimitLog queryYestoday(String merchantid, LocalDate dateTime) {
    Map map = new HashMap();
    map.put("merchantid", merchantid);
    map.put("dateTime", dateTime.toString());
    return (MerchantLimitLog) super.queryOne("queryYestoday", map);
  }

  @Override public MerchantLimitLog queryByMerchantAndDate(String merchantid, LocalDate dateTime) {
    Map map = new HashMap();
    map.put("merchantid", merchantid);
    map.put("dateTime", dateTime);
    return (MerchantLimitLog) super.queryOne("queryByMerchantAndDate", map);
  }

  @Override public void update(MerchantLimitLog merchantLimitLog) {
    super.modify("updateByPrimaryKey", merchantLimitLog);
  }

  @Override
  public MerchantLimitLog insert(MerchantLimitLog merchantLimitLog) {
    super.add(merchantLimitLog);
    return merchantLimitLog;
  }

  @Override public void updateForOrderSyncFail(MerchantLimitLog todayLog) {
    super.modify("updateByPrimaryKeySelective", todayLog);
  }

  @Override
  public String queryOverdueRate(String merchantid) {
    return super.queryOne("queryOverdueRate", merchantid);
  }
}
