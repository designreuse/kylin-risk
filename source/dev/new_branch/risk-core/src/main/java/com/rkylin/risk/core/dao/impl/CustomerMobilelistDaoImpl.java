package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CustomerMobilelistDao;
import com.rkylin.risk.core.entity.CustomerMobilelist;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author qiuxian
 * @create 2016-08-31 15:23
 **/
@Repository("mobilelistDao")
public class CustomerMobilelistDaoImpl extends BaseDaoImpl<CustomerMobilelist> implements
    CustomerMobilelistDao {

  @Override public CustomerMobilelist insert(CustomerMobilelist mobilelist) {
    super.add(mobilelist);
    return mobilelist;
  }

  @Override public CustomerMobilelist queryOne(String customerid) {
    List<CustomerMobilelist> list = super.selectList("queryOneByCustomerid", customerid);
    if (list.isEmpty()) {
      return null;
    } else {
      return list.get(0);
    }
  }

  @Override public void update(CustomerMobilelist mobilelist) {
    super.modify(mobilelist);
  }
}
