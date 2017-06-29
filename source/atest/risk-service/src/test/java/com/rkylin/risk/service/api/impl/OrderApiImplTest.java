package com.rkylin.risk.service.api.impl;

import com.rkylin.risk.order.api.OrderApi;
import com.rkylin.risk.order.bean.ResultInfo;
import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.ApiServiceConstants;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cuixiaofang on 2016-3-28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class OrderApiImplTest {

  @Resource
  private OrderApi orderApiTest;

  @Test
  public void nullOrdercheckTest() {
    ResultInfo resultInfo = orderApiTest.ordercheck(null, null);
    assertThat(resultInfo).isNotNull();
    assertThat(resultInfo.getResultCode()).isEqualTo("1");
    assertThat(resultInfo.getResultMsg()).isEqualTo("该订单为空！");
  }

  @Test
  public void hmacIsNullOrdercheckTest() {
    SimpleOrder orders = new SimpleOrder();
    orders.setOrderId("111");
    orders.setAmount("50000");
    orders.setOrderTime("20151010121212");
    orders.setUserId("123123");
    orders.setGoodsName("geli");
    ResultInfo resultInfo = orderApiTest.ordercheck(orders, "d5bd244edc0ef6f47129b437f08f4b8f");
    assertThat(resultInfo).isNotNull();
    assertThat(resultInfo.getResultCode()).isEqualTo("0");
    assertThat(resultInfo.getResultMsg()).isEqualTo("hmac不为空！");
  }

  @Test
  public void scoreIsZoreOrdercheckTest() {
    SimpleOrder orders = new SimpleOrder();
    orders.setOrderId("111");
    orders.setAmount("50000");
    orders.setOrderTypeId("B2");
    orders.setRootInstCd("222");
    orders.setProductId("123456");
    orders.setOrderDate("20160411");
    orders.setOrderTime("20151010121212");
    orders.setUserId("123123");
    orders.setGoodsName("geli");
    ResultInfo resultInfo = orderApiTest.ordercheck(orders, "68368e792a721a090d7b8b67f8c451dd");
    assertThat(resultInfo).isNotNull();
    assertThat(resultInfo.getResultCode()).isEqualTo("1");
    assertThat(resultInfo.getResultMsg()).isEqualTo("验证失败");
  }

  @Test
  public void scoreIsLoadOrdercheckTest() {
    final CountDownLatch countDownLatch = new CountDownLatch(10);
    ExecutorService executor = Executors.newFixedThreadPool(10);
    for (int i = 1; i < 2; i++) {
      executor.execute(new OrderRun(i, countDownLatch));
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    executor.shutdown();
  }

  class OrderRun implements Runnable {

    private int orderId;
    private CountDownLatch countDownLatch;

    OrderRun(int orderId, CountDownLatch countDownLatch) {
      this.orderId = orderId;
      this.countDownLatch = countDownLatch;
    }

    @Override public void run() {
      try {
        SimpleOrder orders = new SimpleOrder();
        orders.setOrderId("30000" + orderId);
        orders.setCheckorderid("600002" + orderId);
        orders.setAmount("1");
        orders.setOrderTypeId("M20001");
        orders.setUserRelateId("10049");
        orders.setRootInstCd("M000004");
        orders.setProductId("123456");
        orders.setOrderDate("20160822");
        orders.setOrderTime("20160822121212");
        orders.setUserId("123123");
        orders.setGoodsName("geli");
        orders.setUserOrderId("1002");
        orders.setIdentityCard("1234567893");
        String hmacString = new StringBuilder(orders.getOrderId())
            .append(orders.getOrderTypeId())
            .append(orders.getRootInstCd())
            .append(orders.getProductId())
            .append(orders.getUserId())
            .append(ApiServiceConstants.ORDER_API_HMAC)
            .toString();
        ResultInfo resultInfo = orderApiTest.ordercheck(orders,
            DigestUtils.md5Hex(hmacString));
        System.out.println(resultInfo);
      } catch (Exception e) {
        System.out.println("==========================================================" + orderId);
      } finally {
        countDownLatch.countDown();
      }
    }
  }

  public String getHmac(String str) {
    return DigestUtils.md5Hex(str);
  }
}
