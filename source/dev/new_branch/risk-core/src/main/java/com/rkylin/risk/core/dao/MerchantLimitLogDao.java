package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.MerchantLimitLog;
import java.util.List;
import org.joda.time.LocalDate;

/**
 * Created by cuixiaofang on 2016-7-19.
 */
public interface MerchantLimitLogDao {

    List<MerchantLimitLog> queryAll(MerchantLimitLog merchantLimitLog);

    void addMerchantLimitLogBatch(List<MerchantLimitLog> merchantLimitLogs);

    List<MerchantLimitLog> queryServenRate(String merchantid, String dateTime);

    MerchantLimitLog queryYestoday(String merchantid, LocalDate dateTime);

    MerchantLimitLog queryByMerchantAndDate(String merchantid, LocalDate dateTime);

    void update(MerchantLimitLog merchantLimitLog);

    MerchantLimitLog insert(MerchantLimitLog merchantLimitLog);

    void updateForOrderSyncFail(MerchantLimitLog todayLog);

    String queryOverdueRate(String merchantid);
}
