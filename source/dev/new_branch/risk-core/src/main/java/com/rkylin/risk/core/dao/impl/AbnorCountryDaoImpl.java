package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.AbnorCountryDao;
import com.rkylin.risk.core.entity.AbnormalCountrycode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/31.
 */
@Repository("abnorCountryDao")
public class AbnorCountryDaoImpl extends BaseDaoImpl<AbnormalCountrycode> implements
    AbnorCountryDao {
    @Override
    public AbnormalCountrycode queryOne(Integer id) {
        return super.selectOne(id);
    }

    @Override
    public List<AbnormalCountrycode> queryAll(AbnormalCountrycode abnormalCountrycode) {
        return super.selectList("queryAll", abnormalCountrycode);
    }

    @Override
    public void insert(AbnormalCountrycode abnormalCountrycode) {
        super.add(abnormalCountrycode);
    }

    @Override
    public void delete(Integer id) {
        super.del(id);
    }

    @Override
    public void update(AbnormalCountrycode abnormalCountrycode) {
        super.modify(abnormalCountrycode);
    }
}
