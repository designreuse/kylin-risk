package com.rkylin.risk.service.api.impl;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.entity.MerchantLimitLog;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.MerchantLimitLogService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.order.api.OrderStatus;
import com.rkylin.risk.order.bean.ResultInfo;
import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.validator.OrderValidator;
import com.rkylin.risk.service.validator.builder.OrderResultInfoBuilder;
import com.rkylin.risk.service.validator.core.ValidateResult;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;

/**
 * Created by cuixiaofang on 2016-3-30.
 */
@Component("orderStatus")
@Slf4j
public class OrderStatusImpl implements OrderStatus {

  @Resource
  private OrderService orderService;

  @Resource
  private OrderValidator orderValidator;

  @Resource
  private MerchantLimitLogService merchantLimitLogService;

  @Override
  public ResultInfo orderStatus(SimpleOrder simpleOrder, String hmac) {
    log.info("request data:{},hmac:{}", simpleOrder, hmac);
    ValidateResult<OrderResultInfoBuilder> validateResult =
        orderValidator.validate(simpleOrder, hmac);
    OrderResultInfoBuilder builder = validateResult.getBuilder();
    /*if (!validateResult.isPass()) {
      return builder.build();
    }*/

    Order order = new Order();
    order.setOrderid(simpleOrder.getOrderId());
    order.setRespcode(simpleOrder.getRespCode());
    order.setStatusid(simpleOrder.getStatusId());
    Integer counts = orderService.updateByOrderId(order);

    String resultCode = "0", resultMsg = "【风控系统】订单交易同步完成!";

    if (counts == 0) {
      resultCode = "99";
      resultMsg = "【风控系统】无该笔订单交易记录，同步失败!";
      // TODO 订单同步失败回滚金额
      syncFailRallBack(simpleOrder);
    }
    return builder.resultCode(resultCode).resultMsg(resultMsg).build();
  }

  private void syncFailRallBack(SimpleOrder simpleOrder) {
    LocalDate orderDate =
        LocalDate.parse(simpleOrder.getOrderDate(), DateTimeFormat.forPattern("yyyyMMdd"));
    String merchantid = simpleOrder.getUserRelateId();
    MerchantLimitLog todayLog =
        merchantLimitLogService.queryByMerchantAndDate(merchantid, orderDate);
    if (todayLog != null) {
      Amount amount = amountValueOf(simpleOrder.getAmount(), null);
      todayLog.setDaypay(todayLog.getDaypay().subtract(amount));
      todayLog.setMonthpay(todayLog.getMonthpay().subtract(amount));
      todayLog.setSeasonpay(todayLog.getSeasonpay().subtract(amount));
      todayLog.setYearpay(todayLog.getYearpay().subtract(amount));
      todayLog.setMonloannum(todayLog.getMonloannum() - 1);
      todayLog.setSealoannum(todayLog.getSealoannum() - 1);
      todayLog.setYearloannum(todayLog.getYearloannum() - 1);
      todayLog.setDayloannum(todayLog.getDayloannum() - 1);
      merchantLimitLogService.updateForOrderSyncFail(todayLog);
    }
  }
}
