package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.AccountDao;
import com.rkylin.risk.core.entity.Account;
import com.rkylin.risk.core.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 201508240185 on 2015/9/17.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;
    @Override
    public Account queryCustAccount(String customerid) {
        return accountDao.queryCustAccount(customerid);
    }
}
