package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.RiskEventDao;
import com.rkylin.risk.core.entity.RiskEvent;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by v-cuixiaofang on 2015/9/21.
 */
@Repository("riskEventDao")
public class RiskEventDaoImpl extends BaseDaoImpl<RiskEvent> implements RiskEventDao {
    @Override
    public List<RiskEvent> findAll(RiskEvent riskEvent) {
        return Collections.emptyList();
    }


    @Override
    public RiskEvent insertRiskEvent(RiskEvent riskEvent) {
        super.add(riskEvent);
        return riskEvent;
    }

    @Override
    public RiskEvent updateRiskEvent(RiskEvent riskEvent) {
        super.modify("update", riskEvent);
        return riskEvent;
    }

    @Override
    public int deleteRiskEvent(Integer deleteId) {
        return super.del(deleteId);
    }

    @Override
    public List<RiskEvent> queryByCaseId(Integer caseid) {
        return super.selectList("queryByCaseId", caseid);
    }

    @Override
    public RiskEvent queryOne(Integer ids) {
        return super.get(ids);
    }

    @Override
    public List<RiskEvent> exportRiskEventExcel(RiskEvent riskEvent) {
        return super.selectList("queryRiskEventByCondition", riskEvent);
    }
}
