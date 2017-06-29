package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.RoleMenu;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/21.
 */
public interface RoleMenuDao {

  List<RoleMenu> queryAll(Short roleid);

  void delete(Short roleid);

  void insert(RoleMenu roleMenu);

  /**
   * 根据菜单ID查找角色菜单表
   */
  List<RoleMenu> queryByMenuid(Short menuid);

  /**
   * 根据菜单ID删除菜单角色关系
   */
  void deleteRolemenuByMenuid(Short menuid);
}
