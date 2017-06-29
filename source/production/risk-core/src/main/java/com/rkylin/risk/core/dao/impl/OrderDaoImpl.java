package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.OrderDao;
import com.rkylin.risk.core.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int delete(short id) {
        return 0;
    }

    @Override
    public Integer queryDepositExecepNum(Order order) {
        return (Integer) super.queryList("depositExecepNum", order).get(0);
    }

    @Override
    public Order queryOne(short id) {
        return null;
    }

    @Override
    public List<Order> query(Order order) {
        return null;
    }

    @Override
    public Integer queryRecCount(Map map) {
        return (Integer) super.queryList("recCounts", map).get(0);
    }

    @Override
    public List<Order> queryByAmlDub(Integer dubioustxnid) {
        return super.query("queryByAmlDub", dubioustxnid);
    }

    @Override
    public Integer queryDepositTimes(Order order) {
        return (Integer) super.queryList("depositTimes", order).get(0);
    }

    @Override
    public List<Order> queryByCusnum(String customnum) {
        return super.query("queryByCusnum", customnum);
    }

    @Override
    public Integer queryDepositCount(Map map) {
        // return (Integer) super.freeQuery("depositCounts", map).get(0);
        return null;
    }

    @Override
    public List<Order> queryByRespcode(String customerid, String... respcode) {
        Map map = new HashMap();
        map.put("customerid", customerid);
        map.put("respCode", respcode);
        return super.query("queryByRespcode", map);
    }

    @Override
    public List querybycondition(Map map) {
        return super.query("querybycondition", map);
    }

    @Override
    public Integer queryRechargeExecepNum(Order order) {
        return (Integer) super.queryList("rechargeExecepNum", order).get(0);
    }

    @Override
    public List exportByCondition(Map map) {
        return super.query("queryOrderCondition", map);
    }

    @Override
    public List<Order> queryByOrderId(Map map) {
        return super.query("queryByOrderId", map);
    }
}
