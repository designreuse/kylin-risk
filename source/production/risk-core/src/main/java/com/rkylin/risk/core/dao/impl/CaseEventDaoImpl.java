package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CaseEventDao;
import com.rkylin.risk.core.entity.CaseEvent;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-3-28.
 */
@Repository
public class CaseEventDaoImpl extends BaseDaoImpl<CaseEvent> implements CaseEventDao {
  @Override
  public CaseEvent insert(CaseEvent caseEvent) {
    super.add(caseEvent);
    return caseEvent;
  }
}
