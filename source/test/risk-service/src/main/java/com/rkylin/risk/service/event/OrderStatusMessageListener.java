package com.rkylin.risk.service.event;

import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.OrderService;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author ChenFumin
 * @versioin 2016/12/15
 */
@Slf4j
public class OrderStatusMessageListener {

  @Resource
  private OrderService orderService;

  public void updateOrderStatus(List<Order> orders) {
    if (CollectionUtils.isNotEmpty(orders)) {
      log.info("【风控系统】----------开始更改订单状态， 数量: {}-----------", orders.size());
      for (Order order : orders) {
        try {
          orderService.updateStatusid(order);
        } catch (Exception e) {
          log.info("【风控系统】-----------------订单更新状态失败--------------");
          log.info("【风控系统】===========>失败订单详情: {}<==========", order);
        }
      }
      log.info("【风控系统】----------完成订单状态修改， 数量: {}-----------", orders.size());
    } else {
      log.info("【风控系统】----------没有需要更改的订单状态-----------");
    }
  }
}
