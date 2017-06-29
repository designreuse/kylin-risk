package com.rkylin.risk.core.service.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.DubioustxnDao;
import com.rkylin.risk.core.dao.FactorBillRelDao;
import com.rkylin.risk.core.dao.FactorDao;
import com.rkylin.risk.core.dao.ScoreLevelDao;
import com.rkylin.risk.core.entity.CusFactorParam;
import com.rkylin.risk.core.entity.Dubioustxn;
import com.rkylin.risk.core.entity.Factor;
import com.rkylin.risk.core.entity.FactorBillRelation;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.entity.ScoreLevel;
import com.rkylin.risk.core.service.CusFactorParamService;
import com.rkylin.risk.core.service.FactorCallBack;
import com.rkylin.risk.core.service.FactorScoreService;
import com.rkylin.risk.core.service.OrderService;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/24 version: 1.0
 */
@Service
@Slf4j
public class FactorScoreServiceImpl implements FactorScoreService {

    @Resource
    private FactorDao factorDao;

    @Resource
    private FactorBillRelDao factorBillRelDao;

    @Resource
    private ScoreLevelDao scoreLevelDao;

    @Resource
    private OrderService orderService;

    @Resource
    private DubioustxnDao dubioustxnDao;
    @Resource
    private CusFactorParamService cusFactorParamService;

    /**
     * 交易风险因子批量添加数据并且累加每笔订单的计算分值
     *
     * @return 评分
     */
    private double insertBatchScore(String simpleBillId, FactorCallBack callBack) {
        log.info("【dubbo服务】风控系统订单交易因子评分----分数计算");
        BigDecimal amount = new BigDecimal("00");
        List<String> codeLs = callBack.doGetFactor();
        if (codeLs != null) {
            List<FactorBillRelation> factorBillRelationList = Lists.newArrayList();
            for (String code : codeLs) {
                if (code != null) {
                    Factor factor = factorDao.findByCode(code);
                    if (factor != null) {
                        FactorBillRelation factorBillRelation = new FactorBillRelation();
                        factorBillRelation.setBillid(simpleBillId);
                        factorBillRelation.setFactorid(factor.getId());
                        factorBillRelation.setFactorscore(factor.getAccount());
                        amount = amount.add(new BigDecimal(factor.getAccount()));
                        log.info("【dubbo服务】风控系统规则因子名称:{},因子编码:{},计算分值为:{}",
                                factor.getName(), factor.getCode(), factor.getAccount());
                        //                        factorBillRelDao.insert(factorBillRelation);
                        factorBillRelationList.add(factorBillRelation);
                    }
                }
            }
            factorBillRelDao.insertBatch(factorBillRelationList);
        }
        return amount.doubleValue();
    }

    @Override
    public boolean insertDubiousTxn(Order order, CusFactorParam cusFactorParam,
                                    FactorCallBack callBack) {
        String orderid = order.getOrderid();
        double score = insertBatchScore(orderid, callBack);
        Amount riskscore = new Amount(score);
        ScoreLevel scoreLevel = scoreLevelDao.getLevel(score);
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
                //                dubioustxn.setCustomname(customer.getCustomername());
                dubioustxn.setTxnum(orderid);
                dubioustxn.setProductid(order.getProductid());
                //                dubioustxn.setCertificateid(customer.getCertificateid());
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
