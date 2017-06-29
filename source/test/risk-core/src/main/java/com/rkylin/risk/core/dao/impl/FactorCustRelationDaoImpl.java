package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.FactorCustRelationDao;
import com.rkylin.risk.core.entity.FactorCustRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508240185 on 2015/9/16.
 */
@Repository
public class FactorCustRelationDaoImpl extends BaseDaoImpl<FactorCustRelation>
    implements FactorCustRelationDao {
  @Override
  public FactorCustRelation insert(FactorCustRelation relation) {
    super.add(relation);
    return relation;
  }

  @Override
  public void delete(FactorCustRelation relation) {
    super.del(relation.getId());
  }

  @Override
  public void deleteByCust(Long custid) {
    super.del("deleteByCust", custid);
  }

  @Override
  public void insertBatch(List<FactorCustRelation> factorCustRelationList) {
    if (factorCustRelationList != null && !factorCustRelationList.isEmpty()) {
      super.addBatch("insertBatch", factorCustRelationList);
    }
  }
}
