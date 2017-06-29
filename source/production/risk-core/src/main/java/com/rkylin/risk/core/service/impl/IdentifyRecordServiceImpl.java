package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.IdentifyRecordDao;
import com.rkylin.risk.core.entity.IdentifyRecord;
import com.rkylin.risk.core.service.IdentifyRecordService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author qiuxian
 * @create 2016-09-07 11:33
 **/
@Service
public class IdentifyRecordServiceImpl implements IdentifyRecordService {
  @Resource
  private IdentifyRecordDao identifyRecordDao;

  @Override public IdentifyRecord insert(IdentifyRecord record) {
    return identifyRecordDao.insert(record);
  }

  @Override
  public IdentifyRecord queryOne(IdentifyRecord record) {
    return identifyRecordDao.queryOne(record);
  }
}
