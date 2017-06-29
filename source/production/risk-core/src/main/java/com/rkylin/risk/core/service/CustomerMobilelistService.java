package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.CustomerMobilelist;

/**
 * 通讯录
 *
 * @author
 * @create 2016-08-31 15:14
 **/
public interface CustomerMobilelistService {

  CustomerMobilelist queryOne(String customerid);

  CustomerMobilelist insert(CustomerMobilelist mobilelist);

  void update(CustomerMobilelist mobilelist);

}
