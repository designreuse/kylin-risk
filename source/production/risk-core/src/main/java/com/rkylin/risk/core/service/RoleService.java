package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Role;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/19.
 */
public interface RoleService {

  Role insert(String funchoose, String menuchoose, Role role);

  void update(String funchoose, String menuchoose, Role role);

  void delete(Short[] ids);

  Role queryOne(Short id);

  List<Role> query(Role role);

  List<Role> queryAll();

  List<Role> getOperatorRole(Short id);
}
