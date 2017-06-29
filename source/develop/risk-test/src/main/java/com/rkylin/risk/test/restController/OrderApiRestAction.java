package com.rkylin.risk.test.restController;

import com.rkylin.risk.order.api.OrderApi;
import com.rkylin.risk.order.bean.ResultInfo;
import com.rkylin.risk.order.bean.SimpleOrder;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cuixiaofang on 2016-3-31.
 */
@Slf4j
@RestController
@RequestMapping("/orderApi")
public class OrderApiRestAction {

  @Resource
  private OrderApi orderApiTest;

  @RequestMapping("invokeOrderApi")
  public ResultInfo invokeOrderApi(@RequestBody SimpleOrder simpleOrder,
      @RequestParam("hmac") String hmac) {
    return orderApiTest.ordercheck(simpleOrder, hmac);
  }
}
