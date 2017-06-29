package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.entity.Merchant;
import com.rkylin.risk.core.entity.MerchantLimitLog;
import com.rkylin.risk.core.service.MerchantLimitLogService;
import com.rkylin.risk.core.service.OverdueService;
import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.biz.CalculateLoanRuleBiz;
import java.math.RoundingMode;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;

/**
 * 根据规则引擎处理贷款订单 Created by cuixiaofang on 2016-7-21.
 */
@Component("calculateLoanRuleBiz")
@Slf4j
public class CalculateLoanRuleBizImpl implements CalculateLoanRuleBiz {
  @Resource
  private MerchantLimitLogService merchantLimitLogService;
  @Resource
  private OverdueService overdueService;

  @Override
  public LogicRuleBean calculateLoanRule(SimpleOrder simpleOrder, Merchant merchant) {
    //企业号
    String merchantid = simpleOrder.getUserRelateId();
    LocalDate orderDate =
        LocalDate.parse(simpleOrder.getOrderDate(), DateTimeFormat.forPattern("yyyyMMdd"));

    //查询7天的机构放款额数据
    List<MerchantLimitLog> merchantLimitLogs =
        merchantLimitLogService.queryServenRate(merchantid, simpleOrder.getOrderDate());
    //查询当天的放款额
    MerchantLimitLog todayLog =
        merchantLimitLogService.queryByMerchantAndDate(merchantid, orderDate);
    log.info("【dubbo服务】风控系统该机构当日放款信息{}", todayLog);

    LogicRuleBean logicRuleBean =
        calculateLogicRule(merchant,
            calculTodayLog(todayLog, amountValueOf(simpleOrder.getAmount(), null)),
            merchantLimitLogs);
    log.info("【dubbo服务】风控系统规则因子{}", logicRuleBean);
    return logicRuleBean;
  }

  /**
   * 计算放款规则
   */
  private LogicRuleBean calculateLogicRule(Merchant merchant, MerchantLimitLog todayLog,
      List<MerchantLimitLog> merchantLimitLogs) {
    LogicRuleBean logicRuleBean = new LogicRuleBean();
    //日、月、季培训收入
    Amount dayTrainIncome, monthTrainIncome, seasonTrainIncome, trainincomeyear = null;
    // 日、月、季培训人次
    Amount dayTrainNum, monthTrainNum, seasonTrainNum, trainnumyear = null;
    //日、月、季日放款额 除以 日、月、季培训收入 比率
    Amount dayPayRate, monthPayRate, seasonPayRate, yearPayRate = null;
    //日、月、季日放款人次 除以 日、月、季培训人次 比率
    Amount dayNumRate, monthNumRate, seasonNumRate, yearNumRate = null;

    trainincomeyear =
        merchant.getTrainincomeyear() == null || merchant.getTrainincomeyear().isEqualTo(0)
            ? new Amount(1) : merchant.getTrainincomeyear();
    trainnumyear =
        merchant.getTrainnumyear() == null || merchant.getTrainnumyear().isEqualTo(0)
            ? new Amount(1) : merchant.getTrainnumyear();

    int dayNum = merchant.getCreatetime().getYear();
    if ((dayNum % 4 == 0 && dayNum % 100 != 0) || dayNum % 400 == 0) {
      dayTrainIncome = trainincomeyear.divide(
          new Amount(366), 2, RoundingMode.FLOOR);
      if (dayTrainIncome.isEqualTo(0)) {
        dayTrainIncome = new Amount(1);
      }
      dayTrainNum = trainnumyear.divide(
          new Amount(366), 0, RoundingMode.FLOOR);
      if (dayTrainNum.isEqualTo(0)) {
        dayTrainNum = new Amount(1);
      }
    } else {
      dayTrainIncome = trainincomeyear.divide(
          new Amount(365), 2, RoundingMode.FLOOR);
      if (dayTrainIncome.isEqualTo(0)) {
        dayTrainIncome = new Amount(1);
      }
      dayTrainNum = trainnumyear.divide(
          new Amount(365), 0, RoundingMode.FLOOR);
      if (dayTrainNum.isEqualTo(0)) {
        dayTrainNum = new Amount(1);
      }
    }
    //月培训收入
    monthTrainIncome = trainincomeyear.divide(
        new Amount(12), 2, RoundingMode.FLOOR);
    if (monthTrainIncome.isEqualTo(0)) {
      monthTrainIncome = new Amount(1);
    }
    monthTrainNum = trainnumyear.divide(
        new Amount(12), 0, RoundingMode.FLOOR);
    if (monthTrainNum.isEqualTo(0)) {
      monthTrainNum = new Amount(1);
    }
    //季培训收入
    seasonTrainIncome = trainincomeyear.divide(
        new Amount(4), 2, RoundingMode.FLOOR);
    if (seasonTrainIncome.isEqualTo(0)) {
      seasonTrainIncome = new Amount(1);
    }
    seasonTrainNum = trainnumyear.divide(
        new Amount(4), 0, RoundingMode.FLOOR);
    if (seasonTrainNum.isEqualTo(0)) {
      seasonTrainNum = new Amount(1);
    }
    log.info("该机构培训收入信息：dayTrainIncome：{}, monthTrainIncome：{}"
            + ", seasonTrainIncome：{}, yearTrainIncome：{},dayTrainNum：{},"
            + " monthTrainNum：{}, seasonTrainNum：{}，yearTrainNum：{}", dayTrainIncome,
        monthTrainIncome, seasonTrainIncome, trainincomeyear,
        dayTrainNum, monthTrainNum, seasonTrainNum, trainnumyear);
    //计算比值
    dayPayRate = todayLog.getDaypay().divide(
        dayTrainIncome, 2, RoundingMode.FLOOR);
    monthPayRate = todayLog.getMonthpay().divide(
        monthTrainIncome, 2, RoundingMode.FLOOR);
    seasonPayRate = todayLog.getSeasonpay().divide(
        seasonTrainIncome, 2, RoundingMode.FLOOR);
    yearPayRate = todayLog.getYearpay().divide(
        trainincomeyear, 2, RoundingMode.FLOOR);
    dayNumRate = new Amount(todayLog.getDayloannum()).divide(
        dayTrainNum, 2, RoundingMode.FLOOR);
    monthNumRate = new Amount(todayLog.getMonloannum()).divide(
        monthTrainNum, 2, RoundingMode.FLOOR);
    seasonNumRate = new Amount(todayLog.getSealoannum()).divide(
        seasonTrainNum, 2, RoundingMode.FLOOR);
    yearNumRate = new Amount(todayLog.getYearloannum()).divide(
        trainnumyear, 2, RoundingMode.FLOOR);

    String overdueRateStr = overdueService.queryOverdueRate(merchant.getMerchantid());
    log.info("【dubbo服务】该笔订单的逾期率是{}", overdueRateStr);
    logicRuleBean.setDayPayRate(dayPayRate);
    logicRuleBean.setDayNumRate(dayNumRate);
    logicRuleBean.setMonthPayRate(monthPayRate);
    logicRuleBean.setMonthNumRate(monthNumRate);
    logicRuleBean.setSeasonPayRate(seasonPayRate);
    logicRuleBean.setSeasonNumRate(seasonNumRate);
    logicRuleBean.setYearPayRate(yearPayRate);
    logicRuleBean.setYearNumRate(yearNumRate);
    logicRuleBean.setOverdueRate(amountValueOf(overdueRateStr, null));
    if (CollectionUtils.isNotEmpty(merchantLimitLogs)) {
      int i = 0;
      for (MerchantLimitLog merchantLimitLog : merchantLimitLogs) {
        i++;
        switch (i) {
          case 1:
            logicRuleBean.setDayPayGrowthRate(merchantLimitLog.getDaypaygrowth());
            logicRuleBean.setDayNumGrowthRate(merchantLimitLog.getDayloangrowth());
            logicRuleBean.setDayPayRate1(
                merchantLimitLog.getDaypay().divide(
                    dayTrainIncome, 2, RoundingMode.FLOOR));
            logicRuleBean.setDayNumRate1(new Amount(merchantLimitLog.getDayloannum()).
                divide(dayTrainNum, 2, RoundingMode.FLOOR));
            break;
          case 2:
            logicRuleBean.setDayPayGrowthRate1(merchantLimitLog.getDaypaygrowth());
            logicRuleBean.setDayNumGrowthRate1(merchantLimitLog.getDayloangrowth());
            logicRuleBean.setDayPayRate2(
                merchantLimitLog.getDaypay().divide(
                    dayTrainIncome, 2, RoundingMode.FLOOR));
            logicRuleBean.setDayNumRate2(new Amount(merchantLimitLog.getDayloannum()).
                divide(dayTrainNum, 2, RoundingMode.FLOOR));
            break;
          case 3:
            logicRuleBean.setDayPayGrowthRate2(merchantLimitLog.getDaypaygrowth());
            logicRuleBean.setDayNumGrowthRate2(merchantLimitLog.getDayloangrowth());
            break;
          case 4:
            logicRuleBean.setDayPayGrowthRate3(merchantLimitLog.getDaypaygrowth());
            logicRuleBean.setDayNumGrowthRate3(merchantLimitLog.getDayloangrowth());
            break;
          case 5:
            logicRuleBean.setDayPayGrowthRate4(merchantLimitLog.getDaypaygrowth());
            logicRuleBean.setDayNumGrowthRate4(merchantLimitLog.getDayloangrowth());
            break;
          case 6:
            logicRuleBean.setDayPayGrowthRate5(merchantLimitLog.getDaypaygrowth());
            logicRuleBean.setDayNumGrowthRate5(merchantLimitLog.getDayloangrowth());
            break;
          case 7:
            logicRuleBean.setDayPayGrowthRate6(merchantLimitLog.getDaypaygrowth());
            logicRuleBean.setDayNumGrowthRate6(merchantLimitLog.getDayloangrowth());
            break;
          default:
            log.info("【dubbo服务】风控系统机构连续{}日", merchantLimitLogs.size());
            break;
        }
      }
    }
    return logicRuleBean;
  }

  /*
  当日交易累加当比交易
   */
  private MerchantLimitLog calculTodayLog(MerchantLimitLog todayLog, Amount amount) {
    if (todayLog != null) {
      todayLog.setDaypay(todayLog.getDaypay().add(amount));
      todayLog.setMonthpay(todayLog.getMonthpay().add(amount));
      todayLog.setSeasonpay(todayLog.getSeasonpay().add(amount));
      todayLog.setYearpay(todayLog.getYearpay().add(amount));
      todayLog.setMonloannum(todayLog.getMonloannum() + 1);
      todayLog.setSealoannum(todayLog.getSealoannum() + 1);
      todayLog.setYearloannum(todayLog.getYearloannum() + 1);
      todayLog.setDayloannum(todayLog.getDayloannum() + 1);
    } else {
      todayLog = new MerchantLimitLog();
      todayLog.setDaypay(amount);
      todayLog.setMonthpay(amount);
      todayLog.setSeasonpay(amount);
      todayLog.setYearpay(amount);
      todayLog.setMonloannum(1);
      todayLog.setSealoannum(1);
      todayLog.setYearloannum(1);
      todayLog.setDayloannum(1);
    }
    return todayLog;
  }
}
