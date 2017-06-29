package com.rkylin.risk.service.biz.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.CusFactorParam;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.CusFactorParamService;
import com.rkylin.risk.core.service.FactorCallBack;
import com.rkylin.risk.core.service.FactorScoreService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.kie.spring.factorybeans.KieContainerSession;
import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.bean.DepositCode;
import com.rkylin.risk.service.bean.DepositFactor;
import com.rkylin.risk.service.biz.DepositBiz;
import com.rkylin.risk.service.utils.DateUtil;

import java.math.RoundingMode;
import java.util.List;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

/**
 * 提现交易分析 <p> Created by 201508240185 on 2015/10/15.
 */
@Component("depositBiz")
@Slf4j
public class DepositBizImpl extends BaseBizImpl implements DepositBiz {
  @Resource
  private CusFactorParamService cusFactorParamService;
  @Resource
  private FactorScoreService factorScoreService;
  @Resource
  private OrderService orderService;
  @Resource
  KieContainerSession kieContainerSession;
  //当日最大充值方式
  public static final String DRECMAX_TYPE = "01";

  /**
   * 提现交易解析结果
   */
  @Override
  public boolean synchDepositResult(SimpleOrder simpleOrder) {
    //风险因子参数
    CusFactorParam cusFactorParam =
        cusFactorParamService.queryByCustomerid(simpleOrder.getUserId().trim());

    final DepositFactor depositFactor = scaleDepositFactor(simpleOrder, cusFactorParam);
    Order order = reSetOrder(simpleOrder);

    return factorScoreService.insertDubiousTxn(order, setCusParam(cusFactorParam, order),
        new FactorCallBack() {
          @Override
          public List<String> doGetFactor() {
            DepositCode depositCode = getDepositFactorCode(depositFactor);
            List<String> list = Lists.newArrayList();
            list.add(depositCode.getBigamount());
            list.add(depositCode.getCardnumex());
            list.add(depositCode.getDepositpercent());
            list.add(depositCode.getDeposittime());
            list.add(depositCode.getDiv5000());
            list.add(depositCode.getInterval());
            list.add(depositCode.getTimes());
            list.add(depositCode.getHistoryrisk());
            return list;
          }

          @Override
          public Amount defaultScore() {
            return new Amount(Constants.DEFAULT_CUSTOMER_SCORE);
          }

          @Override
          public String getOffmsg() {
            return null;
          }

          @Override
          public boolean getIsoff() {
            return false;
          }
        });
  }

  /**
   * 计算提现数据
   */
  private DepositFactor scaleDepositFactor(SimpleOrder simpleOrder,
      CusFactorParam cusFactorParam) {
    //交易时间异常
    DateTime orderTime = DateUtil.toDateTime(simpleOrder.getOrderTime());
    int tranferTime = orderTime.getHourOfDay();
    final DepositFactor depositFactor = new DepositFactor();

    //充值提现间隔
    Amount hours = null;
    //提现占比异常
    Amount depositExcept = null;
    if (cusFactorParam != null) {
      //历史高风险触发
      depositFactor.setHistoryrisk("true".equals(cusFactorParam.getTrxwrongflag()));
      if (cusFactorParam.getRectime() != null && cusFactorParam.getRectime()
          .toLocalDate().equals(orderTime.toLocalDate())) {
        depositExcept = new Amount(simpleOrder.getAmount()).divide(
            cusFactorParam.getDrecamount(), 2, RoundingMode.FLOOR).multiply(100);
        long diff = orderTime.getMillis() - cusFactorParam.getRectime().getMillis();
        hours = new Amount(diff).divide(new Amount(1000 * 60 * 60), 2, RoundingMode.FLOOR);
      }
    }

    //提现前一小时充值频繁次数
    int reccounts = depositTimes(simpleOrder);

    //大额提现
    Amount bigamount = new Amount(simpleOrder.getAmount()).divide(Constants.DIV100);

    //除以5000是否为整数
    Double aDouble = Double.valueOf(bigamount.toString()) % Constants.DIV5000;
    boolean divide = (new Amount(aDouble).isEqualTo(0)) ? true : false;

    //该客户近1小时提现笔数异常
    //Integer depositCountsExcep = depositExecepNum(simpleOrder);
    Integer depositCountsExcep = 0;

    //提现时间
    depositFactor.setDeposittime(tranferTime);
    //提现时间距离最近一笔充值请求时间间隔
    depositFactor.setInterval(hours);
    //提现前充值频繁次数
    depositFactor.setTimes(reccounts);
    //大额提现
    depositFactor.setBigamount(bigamount);
    //除以5000是否为整数
    depositFactor.setDiv5000(divide);
    //该客户近1小时提现笔数异常
    depositFactor.setCardnumex(depositCountsExcep);
    //提现占比异常
    depositFactor.setDepositpercent(depositExcept);

    return depositFactor;
  }

  public CusFactorParam setCusParam(CusFactorParam cusFactorParam, Order order) {
    if (cusFactorParam == null || cusFactorParam.getRectime()==null||
        !cusFactorParam.getRectime().toLocalDate().equals(order.getOrderdate())) {
      if (cusFactorParam == null) {
        cusFactorParam = new CusFactorParam();
        cusFactorParam.setCustomerid(order.getUserid());
      }
      cusFactorParam.setRectime(null);
      cusFactorParam.setDrecamount(new Amount(0));
      cusFactorParam.setDrecnum((short) 0);
    }
    return cusFactorParam;
  }

  /**
   * 调用规则处理
   */
  private DepositCode getDepositFactorCode(DepositFactor deposit) {
    KieContainer kieContainer =
        kieContainerSession.getBean("com.rkylin.risk.rule:risk-rule-repository");
    KieSession depositKsession = kieContainer.newKieSession("depositKsession");
    DepositCode code = new DepositCode();
    depositKsession.insert(code);
    depositKsession.insert(deposit);
    depositKsession.fireAllRules();
    depositKsession.destroy();
    return code;
  }

  /*
  近一小时充值请求次数
   */
  private Integer depositTimes(SimpleOrder simpleOrder) {
    Order order = new Order();
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    order.setOrdertime(DateTime.parse(simpleOrder.getOrderTime(), format));
    order.setUserid(simpleOrder.getUserId());
    Integer depositTimes = orderService.queryDepositTimes(order);
    log.info("【风控系统】风控规则该客户交易日期1小时充值请求次数:{}", depositTimes);
    return depositTimes;
  }

  /*
  近一小时的提现异常笔数
   */
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
