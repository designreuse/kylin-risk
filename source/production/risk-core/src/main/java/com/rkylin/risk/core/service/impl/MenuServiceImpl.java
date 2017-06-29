package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.MenuDao;
import com.rkylin.risk.core.dao.RoleMenuDao;
import com.rkylin.risk.core.entity.Menu;
import com.rkylin.risk.core.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508031790 on 2015/8/12.
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

  @Resource
  private MenuDao menuDao;
  @Resource
  private RoleMenuDao roleMenuDao;

  @Override
  public Menu addMenu(Menu menu) {
    return menuDao.addMenu(menu);
  }

  @Override
  public Menu updateMenu(Menu menu) {
    return menuDao.updateMenu(menu);
  }

  @Override
  public List<Menu> queryAllMenu() {
    return menuDao.queryAllMenu();
  }

  @Override
  public List<Menu> findByLevel(String level) {
    return menuDao.findByLevel(level);
  }

  @Override
  public Menu findById(Short id) {
    return menuDao.findById(id);
  }

  @Override
  public String findMenuInTree() {
    List<Menu> list = menuDao.queryAllMenu();
    StringBuffer sbf = new StringBuffer();
    // sbf.append("{id:risk,pId:null,name:risk},");
    for (int i = 0; i < list.size(); i++) {
      if (i == list.size() - 1) {
        sbf.append("{id:"
            + list.get(i).getId()
            + ",pId:"
            + list.get(i).getParentid()
            + ",name:'"
            + list.get(i).getMenuname()
            + "',menulevel:'"
            + list.get(i).getMenulevel()
            + "'}");
      } else {
        sbf.append("{id:"
            + list.get(i).getId()
            + ",pId:"
            + list.get(i).getParentid()
            + ",name:'"
            + list.get(i).getMenuname()
            + "',menulevel:'"
            + list.get(i).getMenulevel()
            + "'},");
      }
    }
    return sbf.toString();
  }

  @Override
  public void deleteMenuById(Short id) {
    //删除节点以及对应的roleMenu记录
    menuDao.deleteMenuById(id);
    roleMenuDao.deleteRolemenuByMenuid(id);
  }

  @Override
  public List<Menu> queryMeunByRole(Short roleid) {
    return menuDao.queryMeunByRole(roleid);
  }
}
