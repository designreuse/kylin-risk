package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Loginlog;

/**
 * Created by 201506290344 on 2015/8/14.
 */
public interface LoginLogService {
  Loginlog insert(Loginlog loginlog);

  Loginlog update(Loginlog loginLog);

  Loginlog queryById(Integer id);

  //根据ID查询是否有可用的loginlog
  boolean findIsAvailableSessionId(Integer id);

  //    判断是否是第一次登录
  boolean isFirstLogin(String username);
}
