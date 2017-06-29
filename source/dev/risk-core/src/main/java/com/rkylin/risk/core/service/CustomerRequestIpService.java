package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.CustomerRequestIP;


public interface CustomerRequestIpService {

  CustomerRequestIP queryOne(String customerid);

  Integer addCustomerRequestIP(CustomerRequestIP customerRequestIp);

  void update(CustomerRequestIP customerRequestIp);
}

