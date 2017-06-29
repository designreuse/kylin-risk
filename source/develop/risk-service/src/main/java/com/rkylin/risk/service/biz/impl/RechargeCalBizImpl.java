package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.dto.ScoreRuleBean;
import com.rkylin.risk.core.entity.CusFactorParam;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.CusFactorParamService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.core.service.RiskScoreService;
import com.rkylin.risk.kie.spring.factorybeans.KieContainerSession;
import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.biz.RechargeCalBiz;
import com.rkylin.risk.service.enumtype.OrderType;
import com.rkylin.risk.service.utils.DateUtil;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

/**
 * Created by lina on 2016-6-23.
 */
@Component("rechargeCalBiz")
@Slf4j
public class RechargeCalBizImpl extends BaseBizImpl implements RechargeCalBiz {
  @Resource
  KieContainerSession kieContainerSession;
  @Resource
  private CusFactorParamService cusFactorParamService;
  @Resource
  private OrderService orderService;
  @Resource
  private RiskScoreService riskScoreService;
  /**
   * 当日最大充值方式
   */
  public static final String DRECMAX_TYPE = "01";

  @Override
  public boolean synchRechargeResult(SimpleOrder simpleOrder) {
    //风险因子参数
    CusFactorParam cusFactorParam =
        cusFactorParamService.queryByCustomerid(simpleOrder.getUserId().trim(),
            simpleOrder.getOrderTime());
    DateTime orderTime = DateUtil.toDateTime(simpleOrder.getOrderTime());
    if (cusFactorParam != null) {
      cusFactorParam = updateCusFactorParam(cusFactorParam, simpleOrder, orderTime);
    } else {
      cusFactorParam = insertCusFactorParam(simpleOrder, orderTime);
    }
    final ScoreRuleBean scoreRuleBean = scaleRechargeFactor(simpleOrder,
        cusFactorParam);
    Order order = super.reSetOrder(simpleOrder);
    double scores = calRechargeFactor(scoreRuleBean);
    log.info("【dubbo服务】风控系统订单交易规则评分计算{}分", scores);
    return riskScoreService.insertDubiousTxn(order, cusFactorParam, scores);
  }

  public double calRechargeFactor(ScoreRuleBean customer) {
    log.info("【dubbo服务】风控系统开始规则评分计算");
    KieContainer kieContainer =
        kieContainerSession.getBean("");
    KieSession recharegeKsession = kieContainer.newKieSession("");
    ResultBean score = new ResultBean();
    recharegeKsession.insert(customer);
    recharegeKsession.insert(score);
    recharegeKsession.fireAllRules();
    recharegeKsession.destroy();
    return score.getScore();
  }

  /**
   * 根据订单数据计算
   */
  private ScoreRuleBean scaleRechargeFactor(SimpleOrder order,
      CusFactorParam cusFactorParam) {
    //交易时间异常
    DateTime orderTime = DateUtil.toDateTime(order.getOrderTime());
    int transferHour = orderTime.getHourOfDay();
    //交易日期当天充值请求次数
    int rechcountTimes = cusFactorParam.getDrecnum();
    //大额充值
    Amount bigamount = new Amount(order.getAmount()).divide(Constants.DIV100);
    //交易金额除以5000是否为整数
    Double divideResults = Double.valueOf(bigamount.toString()) % Constants.DIV5000;
    //客户充值异常笔数
    Integer execepnum = rechargeExecepNum(order);
    //充值方式
    String prePaid = null;
    String orderTypeId = order.getOrderTypeId();
    if (OrderType.B1.getValue().equals(orderTypeId)
        || OrderType.B11.getValue().equals(orderTypeId)) {
      prePaid = "gateway";
    }
    if (OrderType.B12.getValue().equals(orderTypeId)
        || OrderType.B13.getValue().equals(orderTypeId)
        || OrderType.B14.getValue().equals(orderTypeId)
        || OrderType.B15.getValue().equals(orderTypeId)) {
      prePaid = "quick";
    }
    final ScoreRuleBean scoreRuleBean = new ScoreRuleBean();
    //交易时间异常
    scoreRuleBean.setRechargetime(new Amount(transferHour));
    //提现前充值频繁
    scoreRuleBean.setRechcount(new Amount(rechcountTimes));
    //大额充值
    scoreRuleBean.setBigamount(bigamount);
    //交易金额除以5000是否为整数
    scoreRuleBean.setDiv5000(divideResults.toString());
    //客户充值异常笔数(按天计算)
    scoreRuleBean.setCardnumex(new Amount(execepnum));
    //充值方式
    scoreRuleBean.setRechway(prePaid);
    //历史高风险触发
    Integer highRiskCount = cusFactorParamService
        .queryHighRiskbyUserId(order.getUserId());
    if (highRiskCount > 0) {
      scoreRuleBean.setHistoryrisk("true");
    } else {
      scoreRuleBean.setHistoryrisk("fasle");
    }
    return scoreRuleBean;
  }

  /**
   * 计算该客户当天充值异常笔数
   */
  private Integer rechargeExecepNum(SimpleOrder simpleOrder) {
    Order order = new Order();
    order.setOrderdate(
        LocalDate.parse(simpleOrder.getOrderDate(), DateTimeFormat.forPattern("yyyyMMdd")));
    order.setUserid(simpleOrder.getUserId());
    Integer rechargeCount = orderService.queryRechargeExecepNum(order);
    return rechargeCount;
  }

  /**
   * 更新客户因子参数
   */
  private CusFactorParam updateCusFactorParam(CusFactorParam cfp, SimpleOrder order,
      DateTime dateTime) {
    Amount orderAmount = new Amount(order.getAmount());
    //判断两个时间相差一天
    if (cfp.getRectime() != null) {
      if (cfp.getDrecmaxamount() != null) {
        if (cfp.getDrecmaxamount().isLesserThan(orderAmount)) {
          cfp.setDrecmaxamount(orderAmount);
        }
      } else {
        cfp.setDrecmaxamount(orderAmount);
      }
      if (cfp.getDrecamount() != null) {
        orderAmount = orderAmount.add(cfp.getDrecamount());
        cfp.setDrecamount(orderAmount);
      } else {
        cfp.setDrecamount(orderAmount);
      }
      if (cfp.getDrecnum() != null) {
        //充值次数
        cfp.setDrecnum((short) (cfp.getDrecnum() + ApiServiceConstants.DREC_NUM));
      } else {
        cfp.setDrecnum(ApiServiceConstants.DREC_NUM);
      }
      cfp.setRectime(dateTime);
    }
    return cusFactorParamService.updateBycustomerid(cfp);
  }

  private CusFactorParam insertCusFactorParam(SimpleOrder order, DateTime orderTime) {
    CusFactorParam cusFactorParam = new CusFactorParam();
    cusFactorParam.setCustomerid(order.getUserId().trim());
    cusFactorParam.setRectime(orderTime);
    Amount orderAmount = new Amount(order.getAmount());
    cusFactorParam.setDrecamount(orderAmount);
    //充值次数
    cusFactorParam.setDrecnum(ApiServiceConstants.DREC_NUM);
    cusFactorParam.setDrecmaxamount(orderAmount);
    cusFactorParam.setDrecmaxtype(DRECMAX_TYPE);
    return cusFactorParamService.insertCusFactorParam(cusFactorParam);
  }
}
