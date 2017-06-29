package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.CaseDao;
import com.rkylin.risk.core.dao.CaseEventDao;
import com.rkylin.risk.core.dao.OrderDao;
import com.rkylin.risk.core.dao.RiskEventDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Case;
import com.rkylin.risk.core.entity.CaseEvent;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.entity.RiskEvent;
import com.rkylin.risk.core.service.OperatorLogService;
import com.rkylin.risk.core.service.RiskEventService;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

/**
 * Created by v-cuixiaofang on 2015/9/21.
 */
@Service("riskEventService")
public class RiskEventServiceImpl implements RiskEventService {

    @Resource
    private RiskEventDao riskEventDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private CaseDao caseDao;
    @Resource
    private CaseEventDao caseEventDao;
    @Resource
    private OperatorLogService operatorLogService;

    @Override
    public List<RiskEvent> findAll(RiskEvent riskEvent) {
        return riskEventDao.findAll(riskEvent);
    }


    @Override
    public RiskEvent insertRiskEvent(RiskEvent riskEvent) {
        return riskEventDao.insertRiskEvent(riskEvent);
    }

    @Override
    public RiskEvent updateRiskEvent(RiskEvent riskEvent, Authorization auth) {
        Short userId = null;
        String userName = "";
        if (auth != null) {
            userId = auth.getUserId();
            userName = auth.getUsername();
        }
        riskEvent.setOperatorid(userId);
        riskEvent.setOperatorname(userName);

        String status = riskEvent.getStatus();
        if ("on".equals(status)) {
            status = "00";
        } else {
            status = "01";
        }
        riskEvent.setStatus(status);
        operatorLogService.insert(userId, userName, "RiskEvent", "编辑风险事件");
        return riskEventDao.updateRiskEvent(riskEvent);
    }

    @Override
    public void deleteRiskEvent(Integer[] deleteIds, Authorization auth) {
        for (Integer deleteId : deleteIds) {
            riskEventDao.deleteRiskEvent(deleteId);
        }
        Short userId = null;
        String userName = "";
        if (auth != null) {
            userId = auth.getUserId();
            userName = auth.getUsername();
        }
        operatorLogService.insert(userId, userName, "RiskEvent", "删除风险事件");
    }

    @Override
    public List<RiskEvent> queryByCaseId(Integer caseid) {
        return riskEventDao.queryByCaseId(caseid);
    }

    @Override
    public RiskEvent queryOne(Integer ids) {
        return riskEventDao.queryOne(ids);
    }

    /**
     * 添加风险事件
     */
    @Override
    public RiskEvent addRiskEvent(RiskEvent riskEvent, String billIds, String path,
                                  Authorization auth) {
        Short userId = null;
        String userName = "";
        if (auth != null) {
            userId = auth.getUserId();
            userName = auth.getUsername();
        }
        riskEvent.setCreatetime(DateTime.now());
        riskEvent.setStatus(Constants.ACTIVE);
        riskEvent.setEventcode(DateTime.now().toString("yyyyMMdd") + riskEvent.getCustomerid());
        riskEvent.setOperatorid(userId);
        riskEvent.setOperatorname(userName);
        riskEvent.setFilepath(path);
        RiskEvent addEvent = riskEventDao.insertRiskEvent(riskEvent);

        if (!StringUtils.isAnyBlank(billIds)) {
            String[] idArry = billIds.split(",");
            List<String> txnIdlist = Arrays.asList(idArry);
            for (String txnid : txnIdlist) {
                Order order = new Order();
                order.setId(Long.parseLong(txnid));
                order.setRiskeventid(addEvent.getId());
                orderDao.update(order);
            }
        }
        operatorLogService.insert(userId, userName, "RiskEvent,SimpleBill", "新增风险事件");
        return addEvent;
    }

    @Override
    public void addCase(Case cases, String addIds, Authorization auth) {
        Short userId = null;
        String userName = "";
        if (auth != null) {
            userId = auth.getUserId();
            userName = auth.getUsername();
        }
        String[] idsarray = addIds.split(",");
        cases.setCasetype(Constants.SYSTEM);
        cases.setStatus(Constants.ACTIVE);
        cases.setOperatorid(userId);
        cases.setOperatorname(userName);
        caseDao.insert(cases);
        Integer caseId = cases.getId();
        for (int i = 0; i < idsarray.length; i++) {
            CaseEvent caseEvent = new CaseEvent();
            caseEvent.setCaseid(caseId);
            caseEvent.setEventid(Integer.parseInt(idsarray[i]));
            caseEvent.setStatus(Constants.ACTIVE);
            caseEventDao.insert(caseEvent);
        }
        operatorLogService.insert(userId, userName, "Case,CaseDubTxn", "添加案例");
    }

    @Override
    public List<RiskEvent> exportRiskEventExcel(RiskEvent riskEvent) {
        return riskEventDao.exportRiskEventExcel(riskEvent);
    }
}
