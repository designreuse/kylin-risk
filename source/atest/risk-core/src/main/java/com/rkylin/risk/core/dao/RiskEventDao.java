package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.RiskEvent;

import java.util.List;

/**
 * Created by v-cuixiaofang on 2015/9/21.
 */
public interface RiskEventDao {

    List<RiskEvent> findAll(RiskEvent riskEvent);

    RiskEvent insertRiskEvent(RiskEvent riskEvent);

    RiskEvent updateRiskEvent(RiskEvent riskEvent);

    int deleteRiskEvent(Integer deleteId);

    List<RiskEvent> queryByCaseId(Integer caseid);

    RiskEvent queryOne(Integer ids);

    List<RiskEvent> exportRiskEventExcel(RiskEvent riskEvent);
}
