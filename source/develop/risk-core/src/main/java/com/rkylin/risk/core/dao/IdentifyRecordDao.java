package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.IdentifyRecord;

/**
 * @author
 * @create 2016-09-07 11:35
 **/
public interface IdentifyRecordDao {

  IdentifyRecord insert(IdentifyRecord record);

  IdentifyRecord queryOne(IdentifyRecord record);
}
