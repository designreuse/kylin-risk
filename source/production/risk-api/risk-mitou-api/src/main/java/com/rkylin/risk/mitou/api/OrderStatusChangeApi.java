package com.rkylin.risk.mitou.api;

/**
 * 更改订单状态接口
 * Created by chenfumin on 2017/1/16.
 */
public interface OrderStatusChangeApi {

  /**
   * @param orderNo 订单号
   * @param status 需要更改的订单状态
   * @param constid 渠道号
   * @return Json
   */
  public String updateOrderStatus(String orderNo, String status, String constid);

}
