package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.OperatorDao;
import com.rkylin.risk.core.entity.Operator;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201506290344 on 2015/8/14.
 */

@Repository("operatorDao")
public class OperatorDaoImpl extends BaseDaoImpl<Operator> implements OperatorDao {
    @Override
    public Operator insert(Operator operator) {
        super.add(operator);
        return operator;
    }

    @Override
    public Operator queryOperatorByUsername(String username) {
        List<Operator> list = super.selectList("queryOperatorByUsername", username.trim());
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Operator update(Operator operator) {
        super.modify(operator);
        return operator;
    }

    @Override
    public List<Operator> queryOperators(Operator operator) {
        return super.selectList("queryOpersByCondition", operator);
    }

    @Override
    public Integer deleteOper(Short id) {
        return super.del(id);
    }

    @Override
    public Operator queryById(Short id) {
        return super.get(id);
    }

    @Override
    public Operator updatePassWord(Operator operator) {
        super.modify("resetpasswd", operator);
        return operator;
    }

    @Override
    public List<Operator> queryByOperatorIds(List list) {
        return super.selectList("queryByOperatorIds", list);
    }
}
