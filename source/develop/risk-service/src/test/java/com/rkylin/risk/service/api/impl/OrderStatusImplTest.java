package com.rkylin.risk.service.api.impl;

import com.rkylin.risk.order.api.OrderStatus;
import com.rkylin.risk.order.bean.ResultInfo;
import com.rkylin.risk.order.bean.SimpleOrder;
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
}
