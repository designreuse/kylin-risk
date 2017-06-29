package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Menu;

import java.util.List;

/**
 * Created by 201508031790 on 2015/8/12.
 */
public interface MenuService {
  Menu addMenu(Menu menu);

  Menu updateMenu(Menu menu);

  Menu findById(Short id);

  List<Menu> queryAllMenu();

  List<Menu> findByLevel(String level);

  String findMenuInTree();

  void deleteMenuById(Short id);

  /**
   * 根据角色查询菜单
   */
  List<Menu> queryMeunByRole(Short roleid);
}
