package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.LoginLogDao;
import com.rkylin.risk.core.entity.Loginlog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201506290344 on 2015/8/19.
 */
@Repository("loginLogDao")
public class LoginLogDaoImpl extends BaseDaoImpl<Loginlog> implements LoginLogDao {
  @Override
  public Loginlog insert(Loginlog loginlog) {
    super.add(loginlog);
    return loginlog;
  }

  @Override
  public Loginlog update(Loginlog loginLog) {
    super.modify(loginLog);
    return loginLog;
  }

  @Override
  public Loginlog queryById(Integer id) {
    return super.get(id);
  }

  @Override
  public List<Loginlog> findByUsername(String username) {
    List<Loginlog> list = super.query("queryLoginLogByUsername", username.trim());
    return list;
  }
}
