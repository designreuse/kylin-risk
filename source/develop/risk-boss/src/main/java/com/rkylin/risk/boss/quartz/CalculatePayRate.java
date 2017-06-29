package com.rkylin.risk.boss.quartz;

import com.google.common.base.Throwables;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.MailBean;
import com.rkylin.risk.core.entity.MerchantLimit;
import com.rkylin.risk.core.entity.MerchantLimitLog;
import com.rkylin.risk.core.exception.RiskRestErrorException;
import com.rkylin.risk.core.service.MerchantLimitLogService;
import com.rkylin.risk.core.service.MerchantLimitService;
import com.rkylin.risk.core.utils.MailUtil;
import com.rkylin.risk.core.utils.Times;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

/**
 * Created by cuixiaofang on 2016-7-25.
 */
@Slf4j
@Component("calculatePayRate")
public class CalculatePayRate {
  @Resource
  private MerchantLimitService merchantLimitService;
  @Resource
  private MerchantLimitLogService merchantLimitLogService;
  @Resource
  private MailUtil mailUtil;

  private static final int NULL_NUM = 0;
  private static final Amount NULL_INCOM = new Amount(0);

  public void calculatePayRate() {
    LocalDate currentTime = new LocalDate();
    log.info("-----------机构额度定时开始---------------");
    log.info("当前时间为{}", currentTime);
    //MerchantLimitLog merchantLimitLog = null;
    try {

      List<MerchantLimit> merchantLimits =
          merchantLimitService.queryAll();
      if (CollectionUtils.isNotEmpty(merchantLimits)) {
        log.info("批量计算机构数量:{}", merchantLimits.size());
        for (MerchantLimit merchantLimit : merchantLimits) {
          log.info("当前计算机构:{}", merchantLimit.toString());
          //今天的交易记录
          MerchantLimitLog today = merchantLimitLogService.queryByMerchantAndDate(
              merchantLimit.getUserrelateid(), currentTime);

          //昨天的交易记录
          MerchantLimitLog yesterday =
              merchantLimitLogService.queryByMerchantAndDate(
                  merchantLimit.getUserrelateid(), currentTime.minusDays(1));
          //前天的交易记录
          MerchantLimitLog beforeYesterday =
              merchantLimitLogService.queryByMerchantAndDate(merchantLimit.getUserrelateid(),
                  currentTime.minusDays(2));
          log.info("此机构{}日交易额数据:{}", currentTime.toString(),
              ReflectionToStringBuilder.toString(today));
          log.info("此机构{}日交易额数据:{}", currentTime.minusDays(1).toString(),
              ReflectionToStringBuilder.toString(yesterday));
          log.info("此机构{}日交易额数据:{}", currentTime.minusDays(2).toString(),
              ReflectionToStringBuilder.toString(beforeYesterday));

          if (today == null) {
            today = new MerchantLimitLog();
            setTodayFromLimit(today, merchantLimit);
            merchantLimitLogService.insert(today);
          }
          if (yesterday == null && beforeYesterday != null) {
            //昨天为空，前天不为空，增长率为-1，其他值从limit中获取
            yesterday = new MerchantLimitLog();
            setYesterdayFromLimit(yesterday, merchantLimit);
            yesterday.setDayloangrowth(
                (beforeYesterday.getDayloannum() == null || beforeYesterday.getDayloannum() == 0)
                    ? new Amount(0) : new Amount(-1));
            yesterday.setDaypaygrowth(
                beforeYesterday.getDaypay() == null || beforeYesterday.getDaypay().isEqualTo(0)
                    ? new Amount(0) : new Amount(-1));
            merchantLimitLogService.insert(yesterday);
          } else if (yesterday == null && beforeYesterday == null) {
            //昨天为空，前天为空，增长率为0，其他值从limit中获取
            yesterday = new MerchantLimitLog();
            setYesterdayFromLimit(yesterday, merchantLimit);
            yesterday.setDayloangrowth(new Amount(0));
            yesterday.setDaypaygrowth(new Amount(0));
            merchantLimitLogService.insert(yesterday);
          } else if (yesterday != null && beforeYesterday == null) {
            //昨天不为空，前天为空，增长率为1
            yesterday.setDayloangrowth(
                yesterday.getDayloannum() == null || yesterday.getDayloannum() == 0
                    ? new Amount(0) : new Amount(1));
            yesterday.setDaypaygrowth(
                yesterday.getDaypay() == null || yesterday.getDaypay().isEqualTo(0)
                    ? new Amount(0) : new Amount(1));
            merchantLimitLogService.update(yesterday);
          } else if (yesterday != null && beforeYesterday != null) {
            //昨天不为空，前天不为空，增长率为（昨天-前天）/前天
            if (beforeYesterday.getDaypay() == null || beforeYesterday.getDaypay().isEqualTo(0)) {
              //前天日交易额为空或零
              if (yesterday.getDaypay() == null || yesterday.getDaypay().isEqualTo(0)) {
                //并且昨天的日交易额为空或零，增长率为0
                yesterday.setDaypaygrowth(new Amount(0));
              } else {
                //并且昨天的日交易额不为零，增长率为1
                yesterday.setDaypaygrowth(new Amount(1));
              }
            } else {
              //前天日交易额不为零
              yesterday.setDaypaygrowth(
                  (yesterday.getDaypay().subtract(beforeYesterday.getDaypay())).divide(
                      beforeYesterday.getDaypay(), 2, RoundingMode.FLOOR));
            }
            if (beforeYesterday.getDayloannum() == null
                || beforeYesterday.getDayloannum() == 0) {
              //前天日交易笔数为空或零
              if (yesterday.getDayloannum() == null || yesterday.getDayloannum() == 0) {
                //并且昨天的日交易笔数为空或零，增长率为0
                yesterday.setDayloangrowth(new Amount(0));
              } else {
                //并且昨天的日交易笔数不为零，增长率为1
                yesterday.setDayloangrowth(new Amount(1));
              }
            } else {
              int loanDif = yesterday.getDayloannum() - beforeYesterday.getDayloannum();
              yesterday.setDayloangrowth(
                  new Amount(loanDif).divide(new Amount(beforeYesterday.getDayloannum()), 2,
                      RoundingMode.FLOOR));
            }
            merchantLimitLogService.update(yesterday);
          }
          log.info("此机构{}日放款额增长率:{}，日放款人数增长率：{}", currentTime.minusDays(1).toString(),
              yesterday.getDaypaygrowth(),
              yesterday.getDayloangrowth());
        }
      }
    } catch (Exception e) {
      sendEmail(currentTime);
      log.info("风控定时异常：{}", Throwables.getStackTraceAsString(e));
    } finally {
      log.info("-----------机构额度定时结束---------------");
    }
  }

  public void setYesterdayFromLimit(MerchantLimitLog yesterday, MerchantLimit limit) {
    yesterday.setUserrelateid(limit.getUserrelateid());
    yesterday.setConstid(limit.getConstid());
    yesterday.setOrderDate(new LocalDate().minusDays(1));
    yesterday.setOrderTime(new DateTime().minusDays(1));
    DateTime yesTime = new DateTime().minusDays(1);
    setFromLimit(yesterday, limit, yesTime);
  }

  public void setTodayFromLimit(MerchantLimitLog today, MerchantLimit limit) {

    today.setUserrelateid(limit.getUserrelateid());
    today.setConstid(limit.getConstid());
    today.setOrderDate(new LocalDate());
    today.setOrderTime(new DateTime());
    DateTime yesTime = new DateTime();
    setFromLimit(today, limit, yesTime);
  }

  public void setFromLimit(MerchantLimitLog log, MerchantLimit limit, DateTime logTime) {
    DateTime limitTime = limit.getOrderTime();
    log.setDaypay(NULL_INCOM);
    log.setDayloannum(NULL_NUM);
    //如果不是同一年，年、季度、月数据清零。
    if (logTime.getYear() != limitTime.getYear()) {
      log.setYearpay(NULL_INCOM);
      log.setSeasonpay(NULL_INCOM);
      log.setMonthpay(NULL_INCOM);
      log.setYearloannum(NULL_NUM);
      log.setSealoannum(NULL_NUM);
      log.setMonloannum(NULL_NUM);
    } else {
      //如果同一年，copy年数据
      log.setYearloannum(limit.getYearloannum());
      log.setYearpay(limit.getYearpay());
      //如果不是同一季度，季度、月数据清零
      if (!Times.isSameSeason(logTime, limitTime)) {
        log.setSeasonpay(NULL_INCOM);
        log.setMonthpay(NULL_INCOM);
        log.setSealoannum(NULL_NUM);
        log.setMonloannum(NULL_NUM);
      } else {
        //是同一季度，copy季度数据
        log.setSeasonpay(limit.getSeasonpay());
        log.setSealoannum(limit.getSealoannum());
        //如果不是同一个月，月数据清零
        if (logTime.getMonthOfYear() != limitTime.getMonthOfYear()) {
          log.setMonthpay(NULL_INCOM);
          log.setMonloannum(NULL_NUM);
        } else {
          //是同一个月，copy月数据
          log.setMonthpay(limit.getMonthpay());
          log.setMonloannum(limit.getMonloannum());
        }
      }
    }
  }

  public void sendEmail(LocalDate localDate) {
    String[] operatorEmails = Constants.SYSTEM_EMAIL_OPERATOR.split(",");
    //创建邮件
    Map map = new HashMap();
    map.put("time", localDate);
    MailBean mailBean = MailBean.builder()
        .data(map)
        .template(Constants.QUARTZ_ERROR_MAIL_TEMPLATE)
        .toEmails(operatorEmails)
        .subject("风控跑批异常")
        .build();

    //发送邮件
    try {
      mailUtil.send(mailBean);
    } catch (MessagingException e) {
      throw new RiskRestErrorException("系统错误!");
    }
  }
}
