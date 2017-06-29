package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.LoginLogDao;
import com.rkylin.risk.core.entity.Loginlog;
import com.rkylin.risk.core.service.LoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201506290344 on 2015/8/19.
 */
@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService {
  @Resource
  private LoginLogDao loginLogDao;

  @Override
  public Loginlog insert(Loginlog loginlog) {
    return loginLogDao.insert(loginlog);
  }

  @Override
  public Loginlog update(Loginlog loginLog) {
    return loginLogDao.update(loginLog);
  }

  @Override
  public Loginlog queryById(Integer id) {
    return loginLogDao.queryById(id);
  }

  @Override
  public boolean findIsAvailableSessionId(Integer id) {
    Loginlog log = loginLogDao.queryById(id);
    return log != null && log.getLogouttime() == null;
  }

  @Override
  public boolean isFirstLogin(String username) {
    List<Loginlog> loginLogs = this.loginLogDao.findByUsername(username);
    return !(loginLogs != null && loginLogs.size() > 1);
  }
}
