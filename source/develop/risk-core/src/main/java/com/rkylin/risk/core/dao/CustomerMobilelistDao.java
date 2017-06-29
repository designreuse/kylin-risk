package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.CustomerMobilelist;

/**
 * 通讯录
 *
 * @author
 * @create 2016-08-31 15:22
 **/
public interface CustomerMobilelistDao {

  CustomerMobilelist queryOne(String customerid);

  CustomerMobilelist insert(CustomerMobilelist mobilelist);

  void update(CustomerMobilelist mobilelist);
}
