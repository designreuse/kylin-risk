package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.FactorCustRelation;

/**
 * Created by 201508240185 on 2015/10/27.
 */
public interface FactorCustRelationService {

    FactorCustRelation insert(FactorCustRelation relation);
    void delete(FactorCustRelation relation);
    void deleteByCust(Long custid);
}
