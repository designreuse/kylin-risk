package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Role;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/19.
 */
public interface RoleDao {

  /**
   * 添加角色
   */
  Role insert(Role role);

  /**
   * 更新角色
   */
  void update(Role role);

  /**
   * 删除角色
   */
  void delete(Short id);

  /**
   * 查询角色详细信息
   */
  Role queryOne(Short id);

  /**
   * 查询
   */
  List<Role> query(Role role);

  /**
   * 查询所有
   */
  List<Role> queryAll();

  /**
   * 根据操作员ID查询角色
   *
   * @returnSet
   */
  List<Role> getOperatorRole(Short id);
}
