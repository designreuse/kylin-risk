package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Dubioustxn;

import java.util.List;
import java.util.Map;

/**
 * Created by v-wangwei on 2015/9/17 0017.
 */
public interface DubioustxnDao {

    Dubioustxn insert(Dubioustxn dubioustxn);

    Dubioustxn queryOne(String ids);

    Dubioustxn getById(Integer id);

    List<Dubioustxn> queryAllDubioustxn(Dubioustxn dubioustxn);

    List<Dubioustxn> queryAllDubioustxnMap(Dubioustxn dubioustxn, Authorization auth);

    void update(Dubioustxn dubioustxn);

    List<Dubioustxn> queryByCaseId(Integer caseid);

    List<Dubioustxn> queryByRiskLevel(String riskLevel);

    /**
     * 修改可疑交易的预警状态
     *
     * @param warnstatus 00：开启；01：关闭
     */
    void updateWarnstatus(String warnstatus, Integer id, String dealopinion);

    List exportDubiousExcel(Map map);

    List<Dubioustxn> queryByTxnum(String orderId);
}
