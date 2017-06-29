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

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;

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

  @Override
  public boolean synchRechargeResult(SimpleOrder simpleOrder) {
    //风险因子参数
    CusFactorParam cusFactorParam = makeCusFactorParam(simpleOrder);
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
    //客户充值异常笔数
    //Integer execepnum = rechargeExecepNum(order);
    Integer execepnum = 0;
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
    rechargeFactor.setDiv5000((new Amount(dou).isEqualTo(0)));
    //客户充值异常笔数(按天计算)
    rechargeFactor.setCardnumex(execepnum);
    //充值方式
    rechargeFactor.setRechway(prePaid);
    //历史高风险触发
    rechargeFactor.setHistoryrisk("true".equals(cusFactorParam.getTrxwrongflag()));
    return rechargeFactor;
  }

  /**
   * 更新客户因子参数
   */
  private CusFactorParam makeCusFactorParam(SimpleOrder order) {
    CusFactorParam cfp = cusFactorParamService.queryByCustomerid(order.getUserId().trim());
    DateTime orderTime = DateUtil.toDateTime(order.getOrderTime());
    if (cfp == null || cfp.getRectime() == null || !cfp.getRectime()
        .toLocalDate().isEqual(orderTime.toLocalDate())) {
      return resetFactorParam(order, cfp);
    } else {
      cfp.setDrecamount(cfp.getDrecamount().add(amountValueOf(order.getAmount(), new Amount(0))));
      cfp.setDrecnum((short) (cfp.getDrecnum() + ApiServiceConstants.DREC_NUM));
      cfp.setRectime(orderTime);
      return cfp;
    }
  }

  public CusFactorParam resetFactorParam(SimpleOrder order, CusFactorParam cusFactorParam) {
    if (cusFactorParam == null) {
      cusFactorParam = new CusFactorParam();
    }
    cusFactorParam.setCustomerid(order.getUserId().trim());
    cusFactorParam.setRectime(DateUtil.toDateTime(order.getOrderTime()));
    cusFactorParam.setDrecamount(amountValueOf(order.getAmount(), new Amount(0)));
    cusFactorParam.setDrecnum(ApiServiceConstants.DREC_NUM);
    return cusFactorParam;
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
