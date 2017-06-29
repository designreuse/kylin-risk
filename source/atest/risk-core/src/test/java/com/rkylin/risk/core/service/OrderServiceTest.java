package com.rkylin.risk.core.service;

import com.rkylin.risk.core.BaseTest;
import com.rkylin.risk.core.entity.Order;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * Created by lina on 2016-10-11.
 */
public class OrderServiceTest extends BaseTest {

  @Resource
  private OrderService orderService;

  @Test
  public void updateByOrderIdTest() {
    Order order = new Order();
    order.setOrderid("");
    order.setRespcode("0");
    order.setStatusid("4");
    orderService.updateByOrderId(order);
  }

  @Test
  public void queryByIdenAndStatusTest() {
    List<Order> orders = orderService.queryByIdenAndStatus("1", new String[] {"4", "8"});
    for (Order order : orders) {
      System.out.println(order.toString());
    }
  }
}
