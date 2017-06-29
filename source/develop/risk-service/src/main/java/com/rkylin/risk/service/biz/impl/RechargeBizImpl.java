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
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.bean.RechargeCode;
import com.rkylin.risk.service.bean.RechargeFactor;
import com.rkylin.risk.service.biz.RechargeBiz;
import com.rkylin.risk.service.enumtype.OrderType;
import com.rkylin.risk.service.utils.DateUtil;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

/**
 * Created by cuixiaofang on 2016-3-25.
 */
@Component("rechargeBiz")
@Slf4j
public class RechargeBizImpl extends BaseBizImpl implements RechargeBiz {
  @Resource
  private CusFactorParamService cusFactorParamService;

  @Resource
  private FactorScoreService factorScoreService;

  @Resource
  private OrderService orderService;

  @Resource
  KieContainerSession kieContainerSession;

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

    final RechargeFactor rechargeFactor = scaleRechargeFactor(simpleOrder, cusFactorParam);

    Order order = super.reSetOrder(simpleOrder);

    //调用规则处理，返回一个评分结果
    return factorScoreService.insertDubiousTxn(order, cusFactorParam, new FactorCallBack() {
      @Override
      public List<String> doGetFactor() {
        RechargeCode code = getRechargeFactorCode(rechargeFactor);
        List<String> list = Lists.newArrayList();
        list.add(code.getRechargetime());
        list.add(code.getRechcount());
        list.add(code.getBigamount());
        list.add(code.getDiv5000());
        list.add(code.getCardnumex());
        list.add(code.getHistoryrisk());
        list.add(code.getRechway());
        return list;
      }

      @Override
      public Amount defaultScore() {
        return new Amount(0);
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
    //if(isPass){
    //    return new ResultInfo("0","验证通过");
    //}else{
    //    return new ResultInfo("1","交易为高风险交易,验证失败");
    //}
  }

  /**
   * 调用规则规则文件处理
   */
  public RechargeCode getRechargeFactorCode(RechargeFactor recharge) {
    KieContainer kieContainer =
        kieContainerSession.getBean("com.rkylin.risk.rule:risk-rule-repository");
    KieSession recharegeKsession = kieContainer.newKieSession("rechargeKsession");
    RechargeCode code = new RechargeCode();
    recharegeKsession.insert(code);
    recharegeKsession.insert(recharge);
    recharegeKsession.fireAllRules();
    recharegeKsession.destroy();
    return code;
  }

  /**
   * 根据订单数据计算
   */
  private RechargeFactor scaleRechargeFactor(SimpleOrder order, CusFactorParam cusFactorParam) {
    //交易时间异常
    DateTime orderTime = DateUtil.toDateTime(order.getOrderTime());
    int transferHour = orderTime.getHourOfDay();
    //交易日期当天充值请求次数
    int rechcountTimes = cusFactorParam.getDrecnum();
    //大额充值
    Amount bigamount = new Amount(order.getAmount()).divide(Constants.DIV100);
    //交易金额除以5000是否为整数
    Double dou = Double.valueOf(bigamount.toString()) % Constants.DIV5000;
    boolean divideResults = (dou == 0d) ? true : false;
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
    final RechargeFactor rechargeFactor = new RechargeFactor();
    //交易时间异常
    rechargeFactor.setRechargetime(transferHour);
    //提现前充值频繁
    rechargeFactor.setRechcount(rechcountTimes);
    //大额充值
    rechargeFactor.setBigamount(bigamount);
    //交易金额除以5000是否为整数
    rechargeFactor.setDiv5000(divideResults);
    //客户充值异常笔数(按天计算)
    rechargeFactor.setCardnumex(execepnum);
    //充值方式
    rechargeFactor.setRechway(prePaid);
    //历史高风险触发
    Integer highRiskCount = cusFactorParamService
        .queryHighRiskbyUserId(order.getUserId());
    if (highRiskCount > 0) {
      rechargeFactor.setHistoryrisk(true);
    } else {
      rechargeFactor.setHistoryrisk(false);
    }
    return rechargeFactor;
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
}
