package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Loginlog;

import java.util.List;

/**
 * Created by 201506290344 on 2015/8/14.
 */
public interface LoginLogDao {
  Loginlog insert(Loginlog loginlog);

  Loginlog update(Loginlog loginLog);

  Loginlog queryById(Integer id);

  List<Loginlog> findByUsername(String username);
}
