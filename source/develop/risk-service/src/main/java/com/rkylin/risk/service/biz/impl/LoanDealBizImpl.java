package com.rkylin.risk.service.biz.impl;

import com.google.common.eventbus.EventBus;
import com.rkylin.oprsystem.system.service.FlowStatusChangeService;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.Dubioustxn;
import com.rkylin.risk.core.entity.Merchant;
import com.rkylin.risk.core.entity.MerchantLimit;
import com.rkylin.risk.core.entity.MerchantLimitLog;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.exception.RiskException;
import com.rkylin.risk.core.service.CustomerinfoService;
import com.rkylin.risk.core.service.DubioustxnService;
import com.rkylin.risk.core.service.MerchantLimitLogService;
import com.rkylin.risk.core.service.MerchantLimitService;
import com.rkylin.risk.core.service.MerchantService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.core.service.OverdueService;
import com.rkylin.risk.core.utils.BeanMappper;
import com.rkylin.risk.core.utils.Times;
import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.bean.ListenerBean;
import com.rkylin.risk.service.biz.CalculateLoanRuleBiz;
import com.rkylin.risk.service.biz.LoanDealBiz;
import com.rkylin.risk.service.biz.LogicRuleCalBiz;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * 风控贷款订单数据接口 Created by cuixiaofang on 2016-7-18.
 */
@Component("loanDealBiz")
@Slf4j
public class LoanDealBizImpl extends BaseBizImpl implements LoanDealBiz {
  @Resource
  private MerchantLimitService merchantLimitService;
  @Resource
  private CalculateLoanRuleBiz calculateLoanRuleBiz;
  @Resource
  private LogicRuleCalBiz logicRuleCalBiz;
  @Resource
  private DubioustxnService dubioustxnService;
  @Resource
  private OrderService orderService;

  @Resource
  private CustomerinfoService customerinfoService;
  @Resource
  private FlowStatusChangeService flowStatusService;
  @Resource
  private MerchantLimitLogService merchantLimitLogService;

  @Resource
  private OverdueService overdueService;
  @Resource
  private MerchantService merchantService;

  private static final int LOAN_NUM = 1;
  private static final String PARTEN_DATE = "yyyyMMdd";

  @Value("${maven.groupPath}")
  private String groupPath;

  @Resource
  private EventBus flowStatusEventBus;

  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public boolean handlingLoanDeal(SimpleOrder simpleOrder) {
    log.info("【dubbo服务】----进入风控贷款订单数据接口-----");
    Order order = orderService.checkExistOrder(simpleOrder.getOrderId());
    // 查看order是否存在
    if (order != null) {
      log.info("【dubbo服务】----本次订单号为【{}】的订单已经处理过，直接返回校验结果----", simpleOrder.getOrderId());
      Dubioustxn dubioustxn = dubioustxnService.queryByTxnum(simpleOrder.getOrderId());
      // TODO 高风险可疑交易关闭报警后，同一个订单号重新发送不拦截
      if (dubioustxn != null
          && Constants.HIGH_LEVEL.equals(dubioustxn.getRisklevel())
          && Constants.ACTIVE.equals(dubioustxn.getWarnstatus())) {
        return false;
      }
      return true;
    }
    // 白名单确认
    String whiteStr = whiteListValidate(simpleOrder.getUserId());
    if (StringUtils.isNotEmpty(whiteStr)) {
      log.info("【dubbo服务】{}在【白名单】中,不进入校验规则,直接通过请求", simpleOrder.getUserId());
      return resultHandler(null, null, whiteStr, simpleOrder);
    }
    //黑名单验证
    String blackStr = blackListValidate(simpleOrder.getUserId(),
        simpleOrder.getIdentityCard());
    if (StringUtils.isNotEmpty(blackStr)) {
      log.info("【dubbo服务】{}在【黑名单】中,不进入校验规则,直接驳回请求", simpleOrder.getUserId());
      return resultHandler(null, blackStr, null, simpleOrder);
    } else {
      ResultBean resultBean = new ResultBean();
      Merchant merchant = merchantService.queryByMerchantid(simpleOrder.getUserRelateId());
      if (merchant == null) {
        log.info("【dubbo服务】风控系统订单{},无该机构信息:{}",
            simpleOrder.getOrderId(), simpleOrder.getUserRelateId());
        resultBean.setOffMsg("风控系统无该机构信息");
        return resultHandler(resultBean, null, null, simpleOrder);
      } else {
        // 课栈
        if ("M000004".equals(simpleOrder.getRootInstCd()) && checkRule(
            RuleConstants.KEZHAN_PERSON_RULE,
            groupPath + RuleConstants.KEZHAN_CHANNEL_ID)) {
          LogicRuleBean logicRuleBean =
              calculateLoanRuleBiz.calculateLoanRule(simpleOrder, merchant);
          resultBean = logicRuleCalBiz.calOrganLogicRuleKezhanwang(logicRuleBean);

          // 帮帮助学
        } else if (RuleConstants.BANGBANG_CHANNEL_ID.equals(simpleOrder.getRootInstCd())
            && checkRule(
            RuleConstants.BANGBANG_PERSON_RULE,
            groupPath + RuleConstants.BANGBANG_CHANNEL_ID)) {
          LogicRuleBean logicRuleBean =
              calculateLoanRuleBiz.calculateLoanRule(simpleOrder, merchant);
          resultBean = logicRuleCalBiz.calOrganLogicRuleBestudent(logicRuleBean);
        }
        return resultHandler(resultBean, null, null, simpleOrder);
      }
    }
  }

  /**
   * 处理交易结果
   */
  private boolean resultHandler(ResultBean resultBean, String blackStr, String whiteStr,
      SimpleOrder simpleOrder) {
    boolean flag = true;
    Order order = super.reSetOrder(simpleOrder);
    if (isEmpty(whiteStr)) {
      Dubioustxn dubioustxn = generateDubioustxn(simpleOrder, blackStr, resultBean, order);
      if (Constants.HIGH_LEVEL.equals(dubioustxn.getRisklevel())) {
        flag = false;
      }
      order.setDubioustxnid(dubioustxn.getId());
    }
    // 插入订单表
    orderService.insert(order);
    return flag;
  }

  /**
   * 生成可疑交易
   */
  public Dubioustxn generateDubioustxn(SimpleOrder simpleOrder, String blackStr,
      ResultBean resultBean, Order order) {

    Dubioustxn dubioustxn = new Dubioustxn();
    dubioustxn.setCustomnum(simpleOrder.getUserId());
    dubioustxn.setWarndate(LocalDate.now());
    dubioustxn.setWarnstatus(Constants.WARN_STATUS_00);
    dubioustxn.setTxnum(simpleOrder.getOrderId());
    dubioustxn.setProductid(simpleOrder.getProductId());
    dubioustxn.setTxncount("1");
    dubioustxn.setTxnaccount(amountValueOf(simpleOrder.getAmount(), new Amount(0)));
    // 设置客户名称
    Customerinfo customerinfo = customerinfoService.queryOne(simpleOrder.getUserId());
    dubioustxn.setCustomname(customerinfo != null ? customerinfo.getCustomername() : null);
    if (blackStr != null) {
      log.info("【dubbo服务】风控系统更新可疑交易表风险等级为高");
      dubioustxn.setRisklevel(Constants.HIGH_LEVEL);
      order.setRiskstatus("2");
      dubioustxn = dubioustxnService.insert(dubioustxn);
      sendMails(blackStr, simpleOrder.getRootInstCd(), simpleOrder.getUserId(), "WARNSET",
          Constants.HIGH_LEVEL);
    } else {
      if ("1".equals(resultBean.getIsOff())) {
        log.info("【dubbo服务】风控系统更新可疑交易表风险等级为高");
        dubioustxn.setRisklevel(Constants.HIGH_LEVEL);
        order.setRiskstatus("3");
        dubioustxn.setRuleid(resultBean.getRuleids());
        dubioustxn = dubioustxnService.insert(dubioustxn);
        setOperateStatus(simpleOrder, "3", resultBean.getOffMsg(), null, Constants.HIGH_LEVEL);
      } else {
        setMerchantLimit(simpleOrder);
        order.setRiskstatus("1");
        if (StringUtils.isNotEmpty(resultBean.getRuleids())) {
          dubioustxn.setRisklevel(Constants.MIDDLE_LEVEL);
          dubioustxn.setRuleid(resultBean.getRuleids());
          log.info("【dubbo服务】风控系统更新可疑交易表,风险等级为中");
          dubioustxn = dubioustxnService.insert(dubioustxn);
          setOperateStatus(simpleOrder, null, null, resultBean.getOffMsg(), Constants.MIDDLE_LEVEL);
        }
      }
    }
    return dubioustxn;
  }

  /*
  设置审核状态,并发邮件
   */
  private void setOperateStatus(SimpleOrder simpleOrder, String status,
      String reason, String riskmsg, String riskLevel) {
    String resureason = StringUtils.isEmpty(reason) ? null : "风控系统返回：" + reason;
    String resuriskmsg = StringUtils.isEmpty(riskmsg) ? null : "风控系统返回：" + riskmsg;
    ListenerBean listenerBean = new ListenerBean();
    listenerBean.setStatus(status);
    listenerBean.setReason(resureason);
    listenerBean.setRiskmsg(resuriskmsg);
    listenerBean.setCheckorderid(simpleOrder.getCheckorderid());
    flowStatusEventBus.post(listenerBean);
    super.sendMails(reason == null ? riskmsg : reason, simpleOrder.getRootInstCd(),
        simpleOrder.getUserId(), "WARNSET", riskLevel);
  }

  //递归调用更新机构限额表
  public void recurUpdateMerchantLimit(MerchantLimit merchantLimit, SimpleOrder simpleOrder,
      int retryTimes) {
    if (retryTimes > 4) {
      throw new RiskException("订单：" + simpleOrder.getOrderId() + "，4次更新机构放款额度表后失败，请重新发送！");
    }
    MerchantLimit restlimit = setMerchantLimitFromOrder(merchantLimit, simpleOrder);
    if (merchantLimitService.updateByUserRelateId(restlimit) == 1) {
      updateLimitLogFromLimit(restlimit, simpleOrder);
      return;
    } else {
      try {
        log.info("订单：{}，第{}次重试更新机构放款额度表失败！", simpleOrder.getOrderId(), retryTimes);
        TimeUnit.MILLISECONDS.sleep(50);
      } catch (Exception e) {
        e.printStackTrace();
      }

      retryTimes++;
      recurUpdateMerchantLimit(null, simpleOrder, retryTimes);
    }
  }

  public void updateLimitLogFromLimit(MerchantLimit merchantLimit, SimpleOrder simpleOrder) {
    MerchantLimitLog limitLog = merchantLimitLogService.queryByMerchantAndDate(
        simpleOrder.getUserRelateId(), merchantLimit.getOrderDate());
    if (limitLog == null) {
      limitLog = new MerchantLimitLog();
      BeanMappper.fastCopy(merchantLimit, limitLog);
      merchantLimitLogService.insert(limitLog);
    } else {
      Integer limitlogId = limitLog.getId();
      BeanMappper.fastCopy(merchantLimit, limitLog);
      limitLog.setId(limitlogId);
      merchantLimitLogService.update(limitLog);
    }
  }

  public MerchantLimit setMerchantLimitFromOrder(MerchantLimit merchantLimit,
      SimpleOrder simpleOrder) {
    if (merchantLimit == null) {
      merchantLimit =
          merchantLimitService.queryByUserrelateid(simpleOrder.getUserRelateId(), null);
    }
    DateTime toTime = merchantLimit.getOrderTime();
    DateTime fromTime = new DateTime();
    //比较时间
    Amount amount = new Amount(simpleOrder.getAmount());
    int toMonth = toTime.getMonthOfYear();
    int fromMonth = fromTime.getMonthOfYear();
    if (toTime.getYear() != fromTime.getYear()) {
      merchantLimit.setYearpay(amount);
      merchantLimit.setSeasonpay(amount);
      merchantLimit.setMonthpay(amount);
      merchantLimit.setDaypay(amount);
      merchantLimit.setYearloannum(LOAN_NUM);
      merchantLimit.setSealoannum(LOAN_NUM);
      merchantLimit.setMonloannum(LOAN_NUM);
      merchantLimit.setDayloannum(LOAN_NUM);
    } else {
      merchantLimit.setYearpay(amount.add(merchantLimit.getYearpay()));
      merchantLimit.setYearloannum(merchantLimit.getYearloannum() + LOAN_NUM);
      if (Times.isSameSeason(toTime, fromTime)) {
        merchantLimit.setSeasonpay(amount.add(merchantLimit.getSeasonpay()));
        merchantLimit.setSealoannum(merchantLimit.getSealoannum() + LOAN_NUM);
        if (toMonth == fromMonth) {
          merchantLimit.setMonthpay(amount.add(merchantLimit.getMonthpay()));
          merchantLimit.setMonloannum(merchantLimit.getMonloannum() + LOAN_NUM);
          if (toTime.getDayOfMonth() == fromTime.getDayOfMonth()) {
            merchantLimit.setDaypay(amount.add(merchantLimit.getDaypay()));
            merchantLimit.setDayloannum(merchantLimit.getDayloannum() + LOAN_NUM);
          } else {
            merchantLimit.setDaypay(amount);
            merchantLimit.setDayloannum(LOAN_NUM);
          }
        } else {
          merchantLimit.setMonthpay(amount);
          merchantLimit.setDaypay(amount);
          merchantLimit.setMonloannum(LOAN_NUM);
          merchantLimit.setDayloannum(LOAN_NUM);
        }
      } else {
        merchantLimit.setSeasonpay(amount);
        merchantLimit.setMonthpay(amount);
        merchantLimit.setDaypay(amount);
        merchantLimit.setSealoannum(LOAN_NUM);
        merchantLimit.setMonloannum(LOAN_NUM);
        merchantLimit.setDayloannum(LOAN_NUM);
      }
    }
    log.info("【dubbo服务】风控系统机构限额数据:{}", merchantLimit);
    merchantLimit.setOrderDate(new LocalDate());
    merchantLimit.setOrderTime(fromTime);
    return merchantLimit;
  }

  /**
   * @param simpleOrder
   * @return
   */
  private MerchantLimit addMerchantLimit(SimpleOrder simpleOrder) {
    MerchantLimit merchantLimit = new MerchantLimit();
    merchantLimit.setUserrelateid(simpleOrder.getUserRelateId());
    merchantLimit.setConstid(simpleOrder.getRootInstCd());
    merchantLimit.setOrderDate(new LocalDate());
    merchantLimit.setOrderTime(new DateTime());
    Amount amount = new Amount(simpleOrder.getAmount());
    merchantLimit.setDaypay(amount);
    merchantLimit.setMonthpay(amount);
    merchantLimit.setSeasonpay(amount);
    merchantLimit.setYearpay(amount);
    merchantLimit.setDayloannum(LOAN_NUM);
    merchantLimit.setMonloannum(LOAN_NUM);
    merchantLimit.setSealoannum(LOAN_NUM);
    merchantLimit.setYearloannum(LOAN_NUM);

    merchantLimitService.insert(merchantLimit);

    log.info("【dubbo服务】风控系统机构限额数据:{}", merchantLimit);
    return merchantLimit;
  }

  private void setMerchantLimit(SimpleOrder simpleOrder) {
    MerchantLimit merchantLimit =
        merchantLimitService.queryByUserrelateid(simpleOrder.getUserRelateId(), null);
    if (merchantLimit != null) {
      recurUpdateMerchantLimit(merchantLimit, simpleOrder, 1);
    } else {
      boolean flag = false;
      try {
        merchantLimit = addMerchantLimit(simpleOrder);
        flag = true;
      } catch (DuplicateKeyException e) {
        recurUpdateMerchantLimit(null, simpleOrder, 1);
      }
      if (flag) {
        //同步更新机构限额记录
        MerchantLimitLog limitLog = new MerchantLimitLog();
        BeanMappper.fastCopy(merchantLimit, limitLog);
        merchantLimitLogService.insert(limitLog);
      }
    }
    return;
  }
}
