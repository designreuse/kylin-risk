package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CustomerinfoDao;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.service.CustomerinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by v-wangwei on 2015/9/6 0006.
 */

@Service("customerService")
public class CustomerinfoServiceImpl implements CustomerinfoService {
    @Resource
    private CustomerinfoDao customerinfoDao;

    @Override
    public Customerinfo queryOne(String customerid) {
        return customerinfoDao.queryOne(customerid);
    }

    @Override
    public List<Customerinfo> queryAll(Customerinfo customerinfo) {
        return customerinfoDao.queryAll(customerinfo);
    }

    @Override
    public Customerinfo insert(Customerinfo customerinfo) {
        return customerinfoDao.insert(customerinfo);
    }

    @Override
    public void addCustomerinfoBatch(List<Customerinfo> customerinfos) {
        customerinfoDao.addCustomerinfoBatch(customerinfos);
    }

    public Integer addCustomerInfo(Customerinfo customerinfo) {
        return customerinfoDao.addCustomerInfo(customerinfo);
    }

    @Override public void update(Customerinfo customerinfo) {
        customerinfoDao.update(customerinfo);
    }
}
