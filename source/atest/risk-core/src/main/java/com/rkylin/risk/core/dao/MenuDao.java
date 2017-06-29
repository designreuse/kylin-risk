package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Menu;

import java.util.List;

/**
 * Created by 201508031790 on 2015/8/12.
 */

public interface MenuDao {

  Menu addMenu(Menu menu);

  Menu updateMenu(Menu menu);

  Menu findById(Short id);

  List<Menu> queryAllMenu();

  List<Menu> findByLevel(String level);

  List<Menu> queryByParentid(Short parentId);

  List<Menu> queryAllBystatus(String status);

  Integer deleteMenuById(Short id);

  List<Menu> queryMeunByRole(Short roleid);
}
