package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.FactorBillRelDao;
import com.rkylin.risk.core.entity.FactorBillRelation;
import com.rkylin.risk.core.service.FactorBillRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 201508031790 on 2015/10/22.
 */
@Service
public class FactorBillRelServiceImpl implements FactorBillRelService {

    @Resource
    private FactorBillRelDao factorBillRelDao;

    @Override
    public FactorBillRelation insert(FactorBillRelation rel) {
        return factorBillRelDao.insert(rel);
    }
}
