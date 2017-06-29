package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.FactorBillRelDao;
import com.rkylin.risk.core.entity.FactorBillRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508031790 on 2015/10/22.
 */
@Repository
public class FactorBillRelDaoImpl extends BaseDaoImpl<FactorBillRelation> implements
    FactorBillRelDao {
  @Override
  public FactorBillRelation insert(FactorBillRelation rel) {
    super.add(rel);
    return rel;
  }

  @Override
  public void insertBatch(List<FactorBillRelation> factorBillRelationList) {
    if (factorBillRelationList != null && !factorBillRelationList.isEmpty()) {
      super.addBatch("insertBatch", factorBillRelationList);
    }
  }
}
