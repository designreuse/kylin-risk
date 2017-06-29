package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Order;
import java.util.List;
import java.util.Map;

/**
 * Created by cuixiaofang on 2016-3-15.
 */
public interface OrderDao {
  /**
   * 添加订单
   */
  Order insert(Order order);

  /**
   * 修改订单
   */
  Order update(Order order);

  /**
   * 计算在交易前一个小时内的充值提交次数
   */
  Integer queryRecCount(Map map);

  List<Order> queryByAmlDub(Integer dubioustxnid);

  List<Order> queryByCusnum(String customnum);

  List querybycondition(Map map);

  Integer queryDepositTimes(Order order);

  Integer queryDepositCount(Map map);

  List<Order> queryByRespcode(String customerid, String... respcode);

  /**
   * 根据订单号修改
   */
  Integer updateByOrderId(Order order);

  Integer queryRechargeExecepNum(Order order);

  Integer queryDepositExecepNum(Order order);

  List<Map> exportByCondition(Map map);

  List<Order> queryByOrderId(Map map);

  Order queryByCheckorderid(Map map);

  void updateOrderStatus(Order order);

  void updateStatusid(Order order);

  List<Order> queryByIdenAndStatus(String identityCard, String[] status);
}

