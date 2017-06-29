package com.rkylin.risk.core.dao.impl;

import com.google.common.collect.Maps;
import com.rkylin.risk.core.dao.OrderDao;
import com.rkylin.risk.core.entity.Order;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ChenFumin on 2016-10-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring-test.xml"})
public class OrderDaoImplTest {

  @Autowired
  private OrderDao orderDao;

  @Test
  public void testQueryByCheckorderid() {
    Map map = Maps.newHashMap();
    map.put("checkorderid", "8096");
    Order order = orderDao.queryByCheckorderid(map);
    Assert.assertNotNull(order);
    Assert.assertEquals("2016101211181700001", order.getOrderid());
  }

  @Test
  public void testUpdateOrderStatus() {
    Order order = new Order();
    order.setOrderid("2016101215175700001");
    order.setRiskstatus("3");
    orderDao.updateOrderStatus(order);
  }

  @Test
  public void queryDepositTimesTest() {
    Map<String, String> map = new HashMap<>();
    map.put("orderId", "2016062315585500001");
    List<Order> orders = orderDao.queryByOrderId(map);
    Order order = orders.get(0);
    System.out.println(order.getOrdertime());
    int i = orderDao.queryDepositTimes(order);
    System.out.println(i);
    assertThat(i).isGreaterThan(0);
  }

  @Test
  public void queryRechargeExecepNumTest() {
    Map<String, String> map = new HashMap<>();
    map.put("orderId", "2016062315585500001");
    List<Order> orders = orderDao.queryByOrderId(map);
    Order order = orders.get(0);
    System.out.println(order.getOrdertime());
    int i = orderDao.queryRechargeExecepNum(order);
    System.out.println(i);
  }

  @Test
  public void queryDepositExecepNumTest() {
    Map<String, String> map = new HashMap<>();
    map.put("orderId", "2016062315585500001");
    List<Order> orders = orderDao.queryByOrderId(map);
    Order order = orders.get(0);
    System.out.println(order.getOrdertime());
    int i = orderDao.queryDepositExecepNum(order);
    System.out.println(i);
  }
}
