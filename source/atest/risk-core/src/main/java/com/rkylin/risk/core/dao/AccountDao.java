package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Account;

/**
 * Created by 201508240185 on 2015/9/17.
 */
public interface AccountDao {
    Account queryCustAccount(String customerid);

}
