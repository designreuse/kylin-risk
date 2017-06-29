package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Functions;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/12.
 */
public interface FunctionService {
  Functions insert(Functions function);

  void delete(Short[] ids);

  void update(Functions function);

  Functions queryOne(Short ids);

  List<Functions> query(Functions function);

  List<Functions> queryAll();

  /**
   * 根据角色查询功能信息
   */
  List<Functions> queryByRole(Short roleid);
}
