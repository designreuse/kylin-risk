package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.OrderDao;
import com.rkylin.risk.core.entity.Order;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by cuixiaofang on 2016-3-15.
 */
@Repository("orderDao")
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {
  @Override
  public Order insert(Order order) {
    super.add(order);
    return order;
  }

  @Override
  public Order update(Order order) {
    super.modify(order);
    return order;
  }

  @Override
  public Integer updateByOrderId(Order order) {

    return super.modify("updateByOrderId", order);
  }

  @Override
  public Integer queryDepositExecepNum(Order order) {
    return super.selectOne("depositExecepNum", order);
  }

  @Override
  public Integer queryRecCount(Map map) {
    return (Integer) super.selectList("recCounts", map).get(0);
  }

  @Override
  public List<Order> queryByAmlDub(Integer dubioustxnid) {
    return super.selectList("queryByAmlDub", dubioustxnid);
  }

  @Override
  public Integer queryDepositTimes(Order order) {
    return this.selectOne("depositTimes", order);
  }

  @Override
  public List<Order> queryByCusnum(String customnum) {
    return super.selectList("queryByCusnum", customnum);
  }

  @Override
  public Integer queryDepositCount(Map map) {
    return null;
  }

  @Override
  public List<Order> queryByRespcode(String customerid, String... respcode) {
    Map map = new HashMap();
    map.put("customerid", customerid);
    map.put("respCode", respcode);
    return super.selectList("queryByRespcode", map);
  }

  @Override
  public List querybycondition(Map map) {
    return super.selectList("querybycondition", map);
  }

  @Override
  public Integer queryRechargeExecepNum(Order order) {
    return selectOne("rechargeExecepNum", order);
  }

  @Override
  public List exportByCondition(Map map) {
    return super.selectList("queryOrderCondition", map);
  }

  @Override
  public List<Order> queryByOrderId(Map map) {
    return super.selectList("queryByOrderId", map);
  }

  @Override
  public Order queryByCheckorderid(Map map) {
    List<Order> orderList = super.selectList("queryByCheckorderid", map);
    return orderList != null && !orderList.isEmpty() ? orderList.get(0) : null;
  }

  @Override
  public void updateOrderStatus(Order order) {
    super.modify("updateOrderStatus", order);
  }

  @Override
  public void updateStatusid(Order order) {
    super.modify("updateStatusid", order);
  }

  @Override public List<Order> queryByIdenAndStatus(String identityCard, String[] status) {
    Map map = new HashMap();
    map.put("identitycard", identityCard);
    map.put("status", status);
    return super.selectList("queryByIdenAndStatus", map);
  }
}
