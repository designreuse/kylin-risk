package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.OrderDao;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuixiaofang on 2016-3-18.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
  @Resource
  private OrderDao orderDao;

  @Override
  public Order insert(Order order) {
    return orderDao.insert(order);
  }

  @Override
  public Order update(Order order) {
    return orderDao.update(order);
  }

  @Override
  public int delete(short id) {
    return 0;
  }

  @Override
  public Order queryOne(short id) {
    return null;
  }

  @Override
  public Integer queryDepositTimes(Order order) {
    return orderDao.queryDepositTimes(order);
  }

  @Override
  public List<Order> query(Order order) {
    return null;
  }

  @Override
  public Integer queryRecCount(DateTime ordertime, String customerid, String type) {
    if (ordertime != null && customerid != null && type != null) {
      Map map = new HashMap();
      if ("minunus_1hour".equals(type)) {
        //交易前1小时充值笔数
        DateTime rectime = ordertime.minusHours(Constants.TXNTIME_MIN);
        map.put("timebegin", rectime);
      } else if ("allday".equals(type)) {
        //交易当天充值笔数
        DateTime dateTime = ordertime.withTimeAtStartOfDay();
        map.put("timebegin", dateTime);
      }
      map.put("timeend", ordertime);
      map.put("customerid", customerid);
      return orderDao.queryRecCount(map);
    }
    return null;
  }

  @Override
  public List<Order> queryByAmlDub(Integer dubioustxnid) {
    return orderDao.queryByAmlDub(dubioustxnid);
  }

  @Override
  public List<Order> queryByCusnum(String customnum) {
    return orderDao.queryByCusnum(customnum);
  }

  @Override
  public Integer queryDepositCount(DateTime txntime, String cardnum) {
    if (txntime != null && cardnum != null) {
      DateTime rectime = txntime.minusHours(Constants.TXNTIME_MIN);
      String num = cardnum.substring(0, 8);
      Map map = new HashMap();
      map.put("timebegin", rectime);
      map.put("timeend", rectime);
      map.put("cardnum", num);
      return orderDao.queryDepositCount(map);
    } else {
      return null;
    }
  }

  @Override
  public List<Order> queryByRespcode(String customerid, String... respcode) {
    return orderDao.queryByRespcode(customerid, respcode);
  }

  @Override
  public List<Map> queryByCondition(Order order, String customername, String riskLevel,
      DateTime timebegin, DateTime timeend) {
    Map map = new HashMap();
    map.put("order", order);
    map.put("customername", customername);
    map.put("risklevel", riskLevel);
    map.put("timebegin", timebegin);
    map.put("timeend", timeend);
    List<Map> mapList = orderDao.querybycondition(map);

    return mapList;
  }

  public Integer queryDepositExecepNum(Order order) {
    return orderDao.queryDepositExecepNum(order);
  }

  @Override
  public Integer updateByOrderId(Order order) {
    return orderDao.updateByOrderId(order);
  }

  @Override
  public Integer queryRechargeExecepNum(Order order) {
    return orderDao.queryRechargeExecepNum(order);
  }

  @Override
  public List<Map> exportByCondition(Order order, String start, String end, String risklevel,
      String customnum, String productall, String[] products,
      String productnull) {
    Map map = new HashMap();
    map.put("order", order);
    map.put("orderid", order.getOrderid());
    map.put("ordertimes", start);
    // map.put("timeend", end);
    map.put("ordertimee", end);
    map.put("productall", productall);
    map.put("products", products);
    map.put("productnull", productnull);
    map.put("risklevel", risklevel);
    map.put("customnum", customnum);
    return orderDao.exportByCondition(map);
  }

  @Override public List<Order> queryByOrderId(String orderId) {
    Map map = new HashMap();
    map.put("orderId", orderId);
    return orderDao.queryByOrderId(map);
  }

  @Override
  public Order checkExistOrder(String orderId) {
    if (StringUtils.isNotEmpty(orderId)) {
      List<Order> orders = this.queryByOrderId(orderId);
      return (orders != null && orders.isEmpty()) ? null : orders.get(0);
    }
    return null;
  }
}
