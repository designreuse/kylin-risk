package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CustomerMobilelistDao;
import com.rkylin.risk.core.entity.CustomerMobilelist;
import com.rkylin.risk.core.service.CustomerMobilelistService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author qiuxian
 * @create 2016-08-31 15:20
 **/

@Service
public class CustomerMobilelistServiceImpl implements CustomerMobilelistService {
  @Resource
  private CustomerMobilelistDao customerMobilelistDao;

  @Override public CustomerMobilelist queryOne(String customerid) {
    return customerMobilelistDao.queryOne(customerid);
  }

  @Override public void update(CustomerMobilelist mobilelist) {
    customerMobilelistDao.update(mobilelist);
  }

  @Override public CustomerMobilelist insert(CustomerMobilelist mobilelist) {

    return customerMobilelistDao.insert(mobilelist);
  }
}
