package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Order;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * 订单接口 Created by cuixiaofang on 2016-3-18.
 */
public interface OrderService {
  /**
   * 添加订单
   */
  Order insert(Order order);

  /**
   * 修改订单
   */
  Order update(Order order);

  /**
   * 修改订单
   */
  Integer updateByOrderId(Order order);

  /**
   * 根据表主键删除一条数据
   */
  int delete(short id);

  /**
   * 根据主键查询数据
   */
  Order queryOne(short id);

  /**
   * 查询所有订单
   */
  List<Order> query(Order order);

  /**
   * 某用户交易日期1小时充值请求次数
   */
  Integer queryDepositTimes(Order order);

  /**
   * 计算提现交易在一个小时前的充值提交次数
   */
  Integer queryRecCount(DateTime txntime, String customerid, String type);

  List<Order> queryByAmlDub(Integer dubioustxnid);

  List<Order> queryByCusnum(String customnum);

  List<Map> queryByCondition(Order order, String customername, String riskLevel,
      DateTime timebegin, DateTime timeend);

  Integer queryDepositCount(DateTime txntime, String cardnum);

  List<Order> queryByRespcode(String customerid, String... respcode);

  /**
   * 该客户当天充值异常笔数
   */
  Integer queryRechargeExecepNum(Order order);

  Integer queryDepositExecepNum(Order order);

  List<Map> exportByCondition(Order order, String start, String end, String risklevel,
      String customnum, String productall, String[] products,
      String productnull);

  List<Order> queryByOrderId(String orderId);

  Order checkExistOrder(String orderId);

  /**
   * 根据工作流ID查询订单
   */
  Order queryByCheckorderid(String checkorderid);

  /**
   * 更新订单风险状态和提示
   */
  void updateOrderStatus(Order order);

  /**
   * 更新订单状态
   */
  void updateStatusid(List<Order> orders);

  /**
   * 根据身份证号和订单状态查询贷款订单信息
   */
  List<Order> queryByIdenAndStatus(String identityCard, String[] status);
}
