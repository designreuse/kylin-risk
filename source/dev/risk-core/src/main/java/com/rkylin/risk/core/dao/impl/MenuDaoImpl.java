package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.MenuDao;
import com.rkylin.risk.core.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508031790 on 2015/8/12.
 */
@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao {
  @Override
  public Menu addMenu(Menu menu) {
    super.add(menu);
    return menu;
  }

  @Override
  public Menu updateMenu(Menu menu) {
    super.modify(menu);
    return menu;
  }

  @Override
  public List<Menu> queryAllMenu() {
    return super.selectAllList();
  }

  @Override
  public List<Menu> findByLevel(String menulevel) {
    return super.selectList("queryBylevel", menulevel);
  }

  @Override
  public Menu findById(Short id) {
    return super.selectOne(id);
  }

  @Override
  public List<Menu> queryByParentid(Short parentId) {
    return super.selectList("queryByParentid", parentId);
  }

  @Override
  public List<Menu> queryAllBystatus(String status) {
    return super.selectList("queryAllBystatus", status);
  }

  @Override
  public Integer deleteMenuById(Short id) {
    return super.del(id);
  }

  @Override
  public List<Menu> queryMeunByRole(Short roleid) {
    return super.selectList("queryMeunByRole", roleid);
  }
}
