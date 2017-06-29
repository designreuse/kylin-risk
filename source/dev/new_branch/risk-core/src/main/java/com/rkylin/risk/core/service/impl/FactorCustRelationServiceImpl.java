package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.FactorCustRelationDao;
import com.rkylin.risk.core.entity.FactorCustRelation;
import com.rkylin.risk.core.service.FactorCustRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 201508240185 on 2015/10/28.
 */
@Service
public class FactorCustRelationServiceImpl implements FactorCustRelationService {

    @Resource
    private FactorCustRelationDao factorCustRelationDao;
    @Override
    public FactorCustRelation insert(FactorCustRelation relation) {
        return factorCustRelationDao.insert(relation);
    }

    @Override
    public void delete(FactorCustRelation relation) {
        factorCustRelationDao.delete(relation);
    }

    @Override
    public void deleteByCust(Long custid) {
        factorCustRelationDao.deleteByCust(custid);
    }
}
