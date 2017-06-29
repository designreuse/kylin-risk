package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.RiskGradeMercDao;
import com.rkylin.risk.core.entity.RiskGradeMerc;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by 201508240185 on 2015/9/16.
 */
@Repository("riskGradeMercDao")
public class RiskGradeMercDaoImpl extends BaseDaoImpl<RiskGradeMerc> implements RiskGradeMercDao {

    @Override
    public List queryPayGradeMerc(Map<String, Object> map) {
        return super.query("queryPayGradeCust", map);
    }

    @Override
    public RiskGradeMerc update(RiskGradeMerc grade) {
        super.modify(grade);
        return grade;
    }

    @Override
    public RiskGradeMerc queryById(Integer id) {
        return super.get(id);
    }
}
