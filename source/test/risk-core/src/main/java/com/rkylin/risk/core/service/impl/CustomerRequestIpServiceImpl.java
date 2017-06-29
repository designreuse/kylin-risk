package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CustomerRequestIpDao;
import com.rkylin.risk.core.entity.CustomerRequestIP;
import com.rkylin.risk.core.service.CustomerRequestIpService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author qiuxian
 * @create 2017-01-03 18:22
 **/
@Service("customerRequestIpService")
public class CustomerRequestIpServiceImpl implements CustomerRequestIpService {
  @Resource
  private CustomerRequestIpDao customerRequestIpDao;

  @Override
  public Integer addCustomerRequestIP(CustomerRequestIP customerRequestIp) {
    return customerRequestIpDao.addCustomerRequestIP(customerRequestIp);
  }

  @Override
  public CustomerRequestIP queryOne(String customerid) {
    return customerRequestIpDao.queryOne(customerid);
  }

  @Override
  public void update(CustomerRequestIP customerRequestIp) {
    customerRequestIpDao.update(customerRequestIp);
  }
}
