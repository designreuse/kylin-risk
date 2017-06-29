package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CustomerRequestIpDao;
import com.rkylin.risk.core.entity.CustomerRequestIP;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author qiuxian
 * @create 2017-01-03 18:23
 **/
@Repository("customerRequestIpDao")
public class CustomerRequestIpDaoImpl extends BaseDaoImpl<CustomerRequestIP> implements
    CustomerRequestIpDao {

  @Override
  public CustomerRequestIP queryOne(String customerid) {
    List<CustomerRequestIP> list = super.selectList("queryByCustomerid", customerid);
    if (list.isEmpty()) {
      return null;
    } else {
      return list.get(0);
    }
  }
  @Override
  public Integer addCustomerRequestIP(CustomerRequestIP customerRequestIp) {
    return super.add(customerRequestIp);
  }

  @Override
  public void update(CustomerRequestIP customerRequestIp) {
        super.modify(customerRequestIp);
  }
}
