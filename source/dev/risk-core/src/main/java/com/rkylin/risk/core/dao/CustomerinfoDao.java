package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Customerinfo;

import java.util.List;

/**
 * Created by v-wangwei on 2015/9/6 0006.
 */
public interface CustomerinfoDao {
    /**
     * 根据商户编号查找商户信息
     *
     * @param customerid
     * @return Customerinfo
     */
    Customerinfo queryOne(String customerid);

    /**
     * 根据给定的条件查询所有的商户信息
     *
     * @param customerinfo
     * @return List<Customerinfo>
     */
    List<Customerinfo> queryAll(Customerinfo customerinfo);

    /**
     * 插入客户信息
     *
     * @param customerinfo
     */
    Customerinfo insert(Customerinfo customerinfo);

    void addCustomerinfoBatch(List<Customerinfo> customerinfos);

    Integer addCustomerInfo(Customerinfo customerinfo);

    void update(Customerinfo customerinfo);
}
