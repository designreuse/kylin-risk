package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.FactorCustRelation;

import java.util.List;

/**
 * Created by 201508240185 on 2015/10/27.
 */
public interface FactorCustRelationDao {

    FactorCustRelation insert(FactorCustRelation relation);
    void delete(FactorCustRelation relation);
    void deleteByCust(Long custid);

    void insertBatch(List<FactorCustRelation> factorCustRelationList);
}
