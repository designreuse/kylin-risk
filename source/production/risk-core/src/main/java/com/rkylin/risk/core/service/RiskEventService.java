package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Case;
import com.rkylin.risk.core.entity.RiskEvent;

import java.util.List;

/**
 * Created by v-cuixiaofang on 2015/9/21.
 */
public interface RiskEventService {

    List<RiskEvent> findAll(RiskEvent riskEvent);

    RiskEvent getRiskEventById(RiskEvent riskEvent);

    RiskEvent insertRiskEvent(RiskEvent riskEvent);

    RiskEvent updateRiskEvent(RiskEvent riskEvent, Authorization auth);

    void deleteRiskEvent(Integer[] deleteIds, Authorization auth);

    List<RiskEvent> queryByCaseId(Integer caseid);

    RiskEvent queryOne(Integer ids);

    /**
     * 添加风险事件
     */
    RiskEvent addRiskEvent(RiskEvent riskEvent, String billIds, String path, Authorization auth);

    /**
     * 添加案例
     */
    void addCase(Case cases, String addIds, Authorization auth);

    List<RiskEvent> exportRiskEventExcel(RiskEvent riskEvent);
}
