package com.rkylin.risk.order.api;

import com.rkylin.risk.order.bean.ResultInfo;
import com.rkylin.risk.order.bean.SimpleOrder;

/**
 * 获取订单系统的订单数据并解析
 *
 * Created by lina on 2016-3-11.
 */
public interface OrderApi {

  /**
   * 交易流水同步
   * @param order
   * @param hmac
   * @return
   */
  ResultInfo ordercheck(SimpleOrder order, String hmac);
}
