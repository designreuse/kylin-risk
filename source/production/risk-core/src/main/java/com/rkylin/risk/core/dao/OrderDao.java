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
     *
     * @param order
     * @return
     */
    Order insert(Order order);

    /**
     * 修改订单
     *
     * @param order
     * @return
     */
    Order update(Order order);

    /**
     * 根据表主键删除一条数据
     *
     * @return
     */
    int delete(short id);

    /**
     * 根据主键查询数据
     *
     * @param id
     * @return
     */
    Order queryOne(short id);

    /**
     * 查询所有订单
     *
     * @return
     */
    List<Order> query(Order order);

    /**
     * 计算在交易前一个小时内的充值提交次数
     *
     * @param map
     * @return
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
     *
     * @param order
     * @return
     */
    Integer updateByOrderId(Order order);

    Integer queryRechargeExecepNum(Order order);

    Integer queryDepositExecepNum(Order order);

    List<Map> exportByCondition(Map map);

    List<Order> queryByOrderId(Map map);
}
