package com.rkylin.risk.test.restController;

import com.rkylin.risk.order.api.OrderApi;
import com.rkylin.risk.order.bean.ResultInfo;
import com.rkylin.risk.order.bean.SimpleOrder;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cuixiaofang on 2016-3-31.
 */
@Slf4j
@RestController
@RequestMapping("/orderApi")
public class OrderApiRestAction {

  @Resource
  private OrderApi orderApi;

private static final String ORDER_API_HMAC="77091510953887013453090194281439";

  @RequestMapping(value = "orderCheck",method = RequestMethod.POST)
  public ResultInfo ordercheck(@RequestBody SimpleOrder simpleOrder) {
    String hmacString = new StringBuilder(simpleOrder.getOrderId())
        .append(simpleOrder.getOrderTypeId())
        .append(simpleOrder.getRootInstCd())
        .append(simpleOrder.getProductId())
        .append(simpleOrder.getUserId())
        .append(ORDER_API_HMAC)
        .toString();
    return orderApi.ordercheck(simpleOrder, DigestUtils.md5Hex(hmacString));
  }
}
