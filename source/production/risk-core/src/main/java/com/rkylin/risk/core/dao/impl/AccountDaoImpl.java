package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.AccountDao;
import com.rkylin.risk.core.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508240185 on 2015/9/17.
 */
@Repository
public class AccountDaoImpl extends BaseDaoImpl<Account> implements AccountDao {
  @Override
  public Account queryCustAccount(String customerid) {
    List<Account> list = super.query("customerQuery", customerid);
    Account account = null;
    if (!list.isEmpty()) {
      account = list.get(0);
    }
    return account;
  }
}
