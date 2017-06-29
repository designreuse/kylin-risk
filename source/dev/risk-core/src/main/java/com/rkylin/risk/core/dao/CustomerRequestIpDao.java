package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.CustomerRequestIP;

/**
 * Created by v-wangwei on 2015/9/6 0006.
 */
public interface CustomerRequestIpDao {
    CustomerRequestIP queryOne(String customerid);

    Integer addCustomerRequestIP(CustomerRequestIP customerRequestIp);

    void update(CustomerRequestIP customerRequestIp);
}
