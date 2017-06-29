package com.rkylin.risk.service.api.impl;

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
import org.springframework.stereotype.Component;

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

  @Override
  public ResultInfo orderStatus(SimpleOrder simpleOrder, String hmac) {
    log.info("request data:{},hmac:{}", simpleOrder, hmac);
    ValidateResult<OrderResultInfoBuilder> validateResult =
        orderValidator.validate(simpleOrder, hmac);
    OrderResultInfoBuilder builder = validateResult.getBuilder();
     Order order = new Order();
    order.setOrderid(simpleOrder.getOrderId());
    order.setRespcode(simpleOrder.getRespCode());
    order.setStatusid(simpleOrder.getStatusId());
    Integer counts = orderService.updateByOrderId(order);

    String resultCode = "0";
    String resultMsg = "【风控系统】订单交易同步完成!";

    if (counts == 0) {
      resultCode = "99";
      resultMsg = "【风控系统】无该笔订单交易记录，同步失败!";
    }
    return builder.resultCode(resultCode).resultMsg(resultMsg).build();
  }


}
