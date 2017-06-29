package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.IdentifyRecord;

/**
 * 认证记录
 *
 * @author
 * @create 2016-09-07 11:22
 **/
public interface IdentifyRecordService {

  IdentifyRecord insert(IdentifyRecord record);

  IdentifyRecord queryOne(IdentifyRecord record);
}
