package com.rkylin.risk.order.api;

import com.rkylin.risk.order.bean.ResultInfo;
import com.rkylin.risk.order.bean.SimpleOrder;
import javax.annotation.Resource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cuixiaofang on 2016-3-28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dubbo-consumer-test.xml"})
public class OrderApiTest {

  @Resource
  private OrderApi orderApiTest;

  private static ClassPathXmlApplicationContext applicationContext;

  @BeforeClass
  public static void beforeClass() {
    applicationContext = new ClassPathXmlApplicationContext("classpath:/risk-spring.xml");
  }

  @AfterClass
  public static void afterClass() {
    if (applicationContext != null) {
      applicationContext.close();
    }
  }

  @Test
  public void nullOrdercheckTest() {
    ResultInfo resultInfo = orderApiTest.ordercheck(null, null);
    assertThat(resultInfo).isNotNull();
    assertThat(resultInfo.getResultCode()).isEqualTo("1");
    assertThat(resultInfo.getResultMsg()).isEqualTo("该订单为空! ");
  }

  @Test
  public void hmacIsNullOrdercheckTest() {
    SimpleOrder orders = new SimpleOrder();
    orders.setOrderId("111");
    orders.setAmount("50000");
    orders.setOrderTypeId("B1");
    orders.setRootInstCd("234");
    orders.setProductId("prd1");
    orders.setOrderTime("20151010121212");
    orders.setUserId("123123");
    orders.setGoodsName("geli");
    ResultInfo resultInfo = orderApiTest.ordercheck(orders, "abc");
    assertThat(resultInfo).isNotNull();
    assertThat(resultInfo.getResultCode()).isEqualTo("0");
    assertThat(resultInfo.getResultMsg()).isEqualTo("hmac不为空！");
  }
}
