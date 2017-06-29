package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Case;
import com.rkylin.risk.core.entity.Dubioustxn;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Created by v-wangwei on 2015/9/17 0017.
 */
public interface DubioustxnService {
    Dubioustxn insert(Dubioustxn dubioustxn);

    Dubioustxn queryById(Integer id);

    Dubioustxn queryByDictCode(String dictcode);

    void update(Dubioustxn dictionaryCode);

    List<Dubioustxn> queryAllDubioustxn(Dubioustxn dubioustxn);

    List<Dubioustxn> queryAllDubioustxnMap(Dubioustxn dubioustxn, Authorization auth);

    List<Dubioustxn> queryByCaseId(Integer caseid);

    List<Dubioustxn> queryByRiskLevel(String riskLevel);

    /**
     * 修改可疑交易的预警状态
     *
     * @param warnstatus 00：开启；01：关闭
     */
    void updateWarnstatus(String warnstatus, Integer id, Authorization auth);

    /**
     * 添加身份证黑名单
     */
    void addIdCardBlackList(Integer id, Authorization auth);

    /**
     * 添加客户黑名单
     */
    void addCustomeBlackList(Integer id, Authorization auth);

    /**
     * 添加案例
     */
    void addCase(Case cases, String addIds, Authorization auth);

    List<Map> exportDubiousExcel(String productall, String[] products, String productnull,
                                 String customnum, String warnstatus, LocalDate beginWarnDate,
                                 LocalDate endWarnDate);

    Dubioustxn queryByTxnum(String orderId);
}
