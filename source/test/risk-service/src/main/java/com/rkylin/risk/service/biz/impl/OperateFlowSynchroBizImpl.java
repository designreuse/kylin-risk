package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.MerchantLimit;
import com.rkylin.risk.core.entity.MerchantLimitLog;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.MerchantLimitLogService;
import com.rkylin.risk.core.service.MerchantLimitService;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.core.utils.BeanMappper;
import com.rkylin.risk.core.utils.Times;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.biz.OperateFlowSynchroBiz;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenFumin on 2016-10-24.
 */
@Slf4j
@Component("operateFlowSynchroBiz")
public class OperateFlowSynchroBizImpl extends BaseBizImpl implements OperateFlowSynchroBiz {

  @Autowired
  private OperateFlowService operateFlowService;

  @Autowired
  private OrderService orderService;

  @Autowired
  private MerchantLimitLogService merchantLimitLogService;

  @Autowired
  private MerchantLimitService merchantLimitService;

  @Override
  @Transactional
  public String synchroStatus(String checkorderid, String status, String reason) {

    OperateFlow operateFlow = operateFlowService.queryByCheckorderid(checkorderid);

    if (operateFlow == null) {
      log.info("【风控系统】工作流ID:{}, 未在风控系统中找到匹配的工作流", checkorderid);
      return "【风控系统】无工作流信息!";
    }
    String operateFlowStatus = operateFlow.getResultstatus();
    log.info("【风控系统】查询到对应工作流，状态码: {}", operateFlowStatus);

    operateFlow.setReason(reason);
    operateFlow.setResultstatus(status);
    operateFlowService.updateOperFlowStatus(operateFlow);

    Order order = orderService.queryByCheckorderid(checkorderid);

    if (order == null) {
      log.info("【风控系统】工作流ID:{}, 无对应的订单信息", checkorderid);
      return "【风控系统】无订单信息!";
    }

    order.setRiskstatus(status);
    order.setStatusid(status);
    orderService.updateOrderStatus(order);

    // 限额回滚
    if (queryRuleGroup(order.getRootinstcd(), Constants.ORDER_INFO) != null &&
        !"1".equals(status)) {
      syncFailRallBack(order);
    }

    return ApiServiceConstants.SUCCESS;
  }

  /**
   * 续议或驳回，回滚该笔订单的限额
   */
  private void syncFailRallBack(Order order) {
    LocalDate orderDate = order.getOrderdate();
    String merchantid = order.getUserrelateid();
    Amount amount = order.getAmount();
    log.info("【风控系统】回滚订单流程, 订单日期:{}, userrealteid:{}, 金额:{}", orderDate, merchantid, amount);
    MerchantLimitLog todayLog =
        merchantLimitLogService.queryByMerchantAndDate(merchantid, orderDate);
    MerchantLimit todayLimit = merchantLimitService.queryByUserrelateid(merchantid, null);
    if(todayLog!=null&&todayLimit!=null){
      // 如果交易日期是当日
      if (orderDate.isEqual(new LocalDate())) {
        todayLog.setDaypay(todayLog.getDaypay().subtract(amount));
        todayLog.setMonthpay(todayLog.getMonthpay().subtract(amount));
        todayLog.setSeasonpay(todayLog.getSeasonpay().subtract(amount));
        todayLog.setYearpay(todayLog.getYearpay().subtract(amount));
        todayLog.setMonloannum(todayLog.getMonloannum() - 1);
        todayLog.setSealoannum(todayLog.getSealoannum() - 1);
        todayLog.setYearloannum(todayLog.getYearloannum() - 1);
        todayLog.setDayloannum(todayLog.getDayloannum() - 1);
        BeanMappper.fastCopy(todayLog, todayLimit);
        // 交易日不是当日
      } else {
        LocalDate orderTime = todayLimit.getOrderDate();
        LocalDate todayTime = new LocalDate();
        // 是同一年
        if (orderTime.getYear() == todayTime.getYear()) {
          todayLimit.setYearpay(todayLimit.getYearpay().subtract(amount));
          todayLimit.setYearloannum(todayLimit.getYearloannum() - 1);
          // 是同一季度
          if (Times.isSameSeason(orderTime, todayTime)) {
            todayLimit.setSeasonpay(todayLimit.getSeasonpay().subtract(amount));
            todayLimit.setSealoannum(todayLimit.getSealoannum() - 1);
            // 是同一个月
            if (orderTime.getMonthOfYear() == todayTime.getMonthOfYear()) {
              todayLimit.setMonthpay(todayLimit.getMonthpay().subtract(amount));
              todayLimit.setMonloannum(todayLimit.getMonloannum() - 1);
            }
          }
        }
        // 更改log表
        todayLog.setDaypay(todayLog.getDaypay().subtract(amount));
        todayLog.setDayloannum(todayLog.getDayloannum() - 1);
      }
      merchantLimitService.updateByUserRelateId(todayLimit);
      merchantLimitLogService.updateForOrderSyncFail(todayLog);
    }
  }
}
