package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CustomerinfoDao;
import com.rkylin.risk.core.entity.Customerinfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by v-wangwei on 2015/9/6 0006.
 */
@Repository("customerDao")
public class CustomerinfoDaoImpl extends BaseDaoImpl<Customerinfo> implements CustomerinfoDao {
    @Override
    public Customerinfo queryOne(String customerid) {
        List<Customerinfo> list = super.selectList("queryOneByCustomerid", customerid);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<Customerinfo> queryAll(Customerinfo customerinfo) {
        return super.selectList("queryPortion", customerinfo);
    }

    @Override
    public Customerinfo insert(Customerinfo customerinfo) {
        super.add(customerinfo);
        return customerinfo;
    }

    @Override
    public void addCustomerinfoBatch(List<Customerinfo> customerinfos) {
        super.addBatch("addCustomerinfoBatch", customerinfos);
    }
    @Override
    public Integer addCustomerInfo(Customerinfo customerinfo) {
        return super.add(customerinfo);
    }

    @Override public void update(Customerinfo customerinfo) {
        super.modify(customerinfo);
    }
}
