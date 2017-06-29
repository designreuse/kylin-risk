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
    order.setId(2L);
    order.setOrderid("1222");
    order.setCheckorderid("0001");
    order.setRespcode("0");
    order.setStatusid("4");
    orderService.update(order);
  }

  @Test
  public void queryByIdenAndStatusTest() {
    List<Order> orders = orderService.queryByIdenAndStatus("1", new String[] {"4", "8"});
    for (Order order : orders) {
      System.out.println(order.toString());
    }
  }

  @Test
  public void updateOrderStatusTest(){
    Order order = new Order();
    order.setOrderid("2016052415082100001");
    order.setStatusid("2");
    order.setRiskstatus("2");
    orderService.updateOrderStatus(order);
  }
}
