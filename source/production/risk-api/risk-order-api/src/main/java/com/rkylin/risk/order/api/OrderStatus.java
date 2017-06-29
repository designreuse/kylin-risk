package com.rkylin.risk.order.api;

import com.rkylin.risk.order.bean.ResultInfo;
import com.rkylin.risk.order.bean.SimpleOrder;

/**
 * 交易流水结果同步 Created by cuixiaofang on 2016-3-30.
 */
public interface OrderStatus {
  /**
   *
   * @param order
   * @param hmac
   * @return
   */
  ResultInfo orderStatus(SimpleOrder order, String hmac);
}
