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
import com.rkylin.risk.service.biz.DepositCalBiz;
import com.rkylin.risk.service.utils.DateUtil;
import java.math.RoundingMode;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

/**
 * Created by lina on 2016-6-23.
 */
@Component("depositCalBiz")
@Slf4j
public class DepositCalBizImpl extends BaseBizImpl implements DepositCalBiz {
  @Resource
  KieContainerSession kieContainerSession;
  @Resource
  private CusFactorParamService cusFactorParamService;
  @Resource
  private RiskScoreService riskScoreService;
  @Resource
  private OrderService orderService;

  @Override
  public boolean synchDepositResult(SimpleOrder simpleOrder) {
    //风险因子参数
    CusFactorParam cusFactorParam =
        cusFactorParamService.queryByCustomerid(simpleOrder.getUserId().trim());

    final ScoreRuleBean customer = scaleDepositFactor(simpleOrder, cusFactorParam);
    Order order = super.reSetOrder(simpleOrder);

    return riskScoreService.insertDubiousTxn(order, cusFactorParam,
        calDepositFactor(customer));
  }

  public double calDepositFactor(ScoreRuleBean customer) {
    KieContainer kieContainer =
        kieContainerSession.getBean("");
    KieSession depositKsession = kieContainer.newKieSession("");
    ResultBean score = new ResultBean();
    depositKsession.insert(customer);
    depositKsession.insert(score);
    depositKsession.fireAllRules();
    depositKsession.destroy();
    return score.getScore();
  }

  /**
   * 计算提现数据
   */
  private ScoreRuleBean scaleDepositFactor(SimpleOrder simpleOrder,
      CusFactorParam cusFactorParam) {
    //交易时间异常
    DateTime orderTime = DateUtil.toDateTime(simpleOrder.getOrderTime());
    int tranferTime = orderTime.getHourOfDay();

    //充值提现间隔
    Amount hours = null;
    if (cusFactorParam != null) {
      long diff = orderTime.getMillis() - cusFactorParam.getRectime().getMillis();
      hours = new Amount(diff).divide(new Amount(1000 * 60 * 60), 2, RoundingMode.FLOOR);
    } else {
      hours = new Amount("13");
    }

    //提现前充值频繁次数
    int reccounts = depositTimes(simpleOrder);

    //大额提现
    Amount bigamount = new Amount(simpleOrder.getAmount()).divide(Constants.DIV100);

    //除以5000是否为整数
    Double divide = Double.valueOf(bigamount.toString()) % Constants.DIV5000;

    //该客户近1小时提现笔数异常
    Integer depositCountsExcep = depositExecepNum(simpleOrder);

    //提现占比异常
    Amount depositExcept = null;
    if (null != cusFactorParam && null != cusFactorParam.getDrecamount()) {
      depositExcept = new Amount(simpleOrder.getAmount()).divide(
          cusFactorParam.getDrecamount(), 2, RoundingMode.FLOOR);
    } else {
      depositExcept = new Amount("0");
    }

    final ScoreRuleBean customerBean = new ScoreRuleBean();
    //提现时间
    customerBean.setDeposittime(new Amount(tranferTime));
    //提现时间距离最近一笔充值请求时间间隔
    customerBean.setInterval(hours);
    //提现前充值频繁次数
    customerBean.setTimes(new Amount(reccounts));
    //大额提现
    customerBean.setBigamount(bigamount);
    //除以5000是否为整数
    customerBean.setDiv5000(divide.toString());
    //该客户近1小时提现笔数异常
    customerBean.setCardnumex(new Amount(depositCountsExcep));
    //提现占比异常
    customerBean.setDepositpercent(depositExcept.multiply(100));
    //历史高风险触发
    Integer highRiskCount =
        cusFactorParamService.queryHighRiskbyUserId(simpleOrder.getUserId());
    if (highRiskCount > 0) {
      customerBean.setHistoryrisk("true");
    } else {
      customerBean.setHistoryrisk("false");
    }
    return customerBean;
  }

  private Integer depositTimes(SimpleOrder simpleOrder) {
    Order order = new Order();
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    order.setOrdertime(DateTime.parse(simpleOrder.getOrderTime(), format));
    order.setUserid(simpleOrder.getUserId());
    Integer depositTimes = orderService.queryDepositTimes(order);
    log.info("【风控系统】风控规则该客户交易日期1小时充值请求次数:{}", depositTimes);
    return depositTimes;
  }

  private Integer depositExecepNum(SimpleOrder simpleOrder) {
    Order order = new Order();
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    order.setOrdertime(DateTime.parse(simpleOrder.getOrderTime(), format));
    order.setUserid(simpleOrder.getUserId());
    Integer depositCount = orderService.queryDepositExecepNum(order);
    log.info("【风控系统】风控规则记录该客户近1小时提现笔数异常:{}", depositCount);
    return depositCount;
  }
}
