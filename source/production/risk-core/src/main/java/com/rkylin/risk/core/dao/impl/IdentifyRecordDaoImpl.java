package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.IdentifyRecordDao;
import com.rkylin.risk.core.entity.IdentifyRecord;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author qiuxian
 * @create 2016-09-07 11:37
 **/
@Repository("identifyRecordDao")
public class IdentifyRecordDaoImpl extends BaseDaoImpl<IdentifyRecord> implements
    IdentifyRecordDao {

  @Override public IdentifyRecord insert(IdentifyRecord record) {
    super.add(record);
    return record;
  }

  @Override public IdentifyRecord queryOne(IdentifyRecord record) {
    List<IdentifyRecord> list = super.query("queryBycondition", record);
    if (list.isEmpty()) {
      return null;
    } else {
      return list.get(0);
    }
  }
}
