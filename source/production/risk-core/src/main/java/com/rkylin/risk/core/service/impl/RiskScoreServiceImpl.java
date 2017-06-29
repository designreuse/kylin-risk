package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.DubioustxnDao;
import com.rkylin.risk.core.dao.ScoreLevelDao;
import com.rkylin.risk.core.entity.CusFactorParam;
import com.rkylin.risk.core.entity.Dubioustxn;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.entity.ScoreLevel;
import com.rkylin.risk.core.service.CusFactorParamService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.core.service.RiskScoreService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cuixiaofang on 2016-6-27.
 */
@Service("riskScoreService")
@Slf4j
public class RiskScoreServiceImpl implements RiskScoreService {
    @Resource
    private ScoreLevelDao scoreLevelDao;

    @Resource
    private OrderService orderService;

    @Resource
    private DubioustxnDao dubioustxnDao;
    @Resource
    private CusFactorParamService cusFactorParamService;

    @Override
    public boolean insertDubiousTxn(Order order, CusFactorParam cusFactorParam, double scores) {
        String orderid = order.getOrderid();
        Amount riskscore = new Amount(scores);
        ScoreLevel scoreLevel = scoreLevelDao.getLevel(scores);
        boolean trxPass = true;
        if (scoreLevel != null) {
            if (Constants.HIGH_LEVEL.equals(scoreLevel.getRisklevel())) {
                //可疑交易
                log.info("【dubbo服务】风控系统更新可疑交易表");
                Dubioustxn dubioustxn = new Dubioustxn();
                dubioustxn.setTxnaccount(order.getAmount());
                dubioustxn.setRisklevel(Constants.HIGH_LEVEL);
                dubioustxn.setWarndate(LocalDate.now());
                dubioustxn.setWarnstatus(Constants.WARN_STATUS_00);
                dubioustxn.setCustomnum(order.getUserid());
                dubioustxn.setTxnum(orderid);
                dubioustxn.setProductid(order.getProductid());
                dubioustxn = dubioustxnDao.insert(dubioustxn);
                order.setDubioustxnid(dubioustxn.getId());
                order.setScore(riskscore);
                if (cusFactorParam != null) {
                    cusFactorParam.setTrxwrongflag("true");
                    cusFactorParamService.updateByUserIdAndTime(cusFactorParam);
                }
                trxPass = false;
            } else {
                order.setScore(riskscore);
            }
            //插入订单表Order数据
            log.info("【dubbo服务】风控系统更新更新订单表");
            orderService.insert(order);
        }
        return trxPass;
    }
}
