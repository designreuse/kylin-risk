package com.rkylin.risk.service.api.impl;

import com.rkylin.risk.order.api.OrderStatus;
import com.rkylin.risk.order.bean.ResultInfo;
import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.ApiServiceConstants;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-4-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class OrderStatusImplTest {

  @Resource
  private OrderStatus orderStatus;

  @Test
  public void orderStatusSimpleOrderIsNullTest() {
    ResultInfo resultInfo = orderStatus.orderStatus(null, null);
    assertThat(resultInfo).isNotNull();
    assertThat(resultInfo.getResultCode()).isEqualTo("99");
    assertThat(resultInfo.getResultMsg()).isEqualTo("订单为空!");
  }

  @Test
  public void orderStatusHmacrIsNullTest() {
    ResultInfo resultInfo = orderStatus.orderStatus(new SimpleOrder(), null);
    assertThat(resultInfo).isNotNull();
    assertThat(resultInfo.getResultCode()).isEqualTo("99");
    assertThat(resultInfo.getResultMsg()).isEqualTo("签名字符串为空!");
  }

  @Test
  public void orderStatusSyncFail() {
    SimpleOrder orders = new SimpleOrder();
    orders.setOrderId("12345d21");
    orders.setCheckorderid("CHECK00001");
    orders.setAmount("50");
    orders.setOrderTypeId("M20001");
    orders.setUserRelateId("10049");
    orders.setRootInstCd("M000004");
    orders.setProductId("123456");
    orders.setOrderDate("20160829");
    orders.setOrderTime("20161011121212");
    orders.setUserId("123123");
    orders.setGoodsName("geli");
    orders.setUserOrderId("1002");
    orders.setIdentityCard("123456789");
    orders.setRespCode("OK");
    String hmacString = new StringBuilder(orders.getOrderId())
        .append(orders.getOrderTypeId())
        .append(orders.getRootInstCd())
        .append(orders.getProductId())
        .append(orders.getUserId())
        .append(ApiServiceConstants.ORDER_API_HMAC)
        .toString();
    ResultInfo resultInfo = orderStatus.orderStatus(orders, hmacString);
    assertThat(resultInfo).isNotNull();
    assertThat(resultInfo.getResultCode()).isEqualTo("99");
  }
}
