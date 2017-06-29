package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.FactorBillRelation;

import java.util.List;

/**
 * Created by 201508031790 on 2015/10/22.
 */
public interface FactorBillRelDao {
  FactorBillRelation insert(FactorBillRelation rel);

  void insertBatch(List<FactorBillRelation> factorBillRelationList);
}
