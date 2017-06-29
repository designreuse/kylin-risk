package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.RoleDao;
import com.rkylin.risk.core.dao.RoleFunctionDao;
import com.rkylin.risk.core.dao.RoleMenuDao;
import com.rkylin.risk.core.entity.Role;
import com.rkylin.risk.core.entity.RoleFunction;
import com.rkylin.risk.core.entity.RoleMenu;
import com.rkylin.risk.core.service.RoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 201507270241 on 2015/8/19.
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
  @Resource
  private RoleDao roleDao;
  @Resource
  private RoleMenuDao roleMenuDao;
  @Resource
  private RoleFunctionDao roleFunctionDao;

  @Override
  public Role insert(String funchoose, String menuchoose, Role role) {
    if (role != null) {
      role.setStatus(Constants.ACTIVE);
      roleDao.insert(role);
    }

       /*维护角色功能信息begin*/
    String[] funcids = funchoose.split(",");
    List<String> funidLis = Arrays.asList(funcids);
    for (String funcid : funidLis) {
      RoleFunction roleFunction = new RoleFunction();
      roleFunction.setFunctionid(Short.parseShort(funcid));
      if (role != null) {
        roleFunction.setRoleid(role.getId());
      }
      roleFunctionDao.insert(roleFunction);
    }
    /*维护角色功能信息end*/
    /*维护角色菜单信息begin*/
    String[] menuids = menuchoose.split(",");
    List<String> menuidLis = Arrays.asList(menuids);
    for (String menuid : menuidLis) {
      RoleMenu roleMenu = new RoleMenu();
      roleMenu.setMenuid(Short.parseShort(menuid));
      if (role != null) {
        roleMenu.setRoleid(role.getId());
      }
      roleMenuDao.insert(roleMenu);
    }
    /*维护角色菜单信息end*/
    return null;
  }

  @Override
  public void update(String funchoose, String menuchoose, Role role) {
    if (role != null) {
      roleDao.update(role);
    }
        /*维护角色菜单信息（先把原有的关系信息删除，然后再新建关系）begin*/
    if (role != null && menuchoose != null && !"".equals(menuchoose)) {
      roleMenuDao.delete(role.getId());
      String[] menuids = menuchoose.split(",");
      List<String> menuidLis = Arrays.asList(menuids);
      for (String menuid : menuidLis) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenuid(Short.parseShort(menuid));
        roleMenu.setRoleid(role.getId());
        roleMenuDao.insert(roleMenu);
      }
    }
        /*维护角色菜单信息end*/
        /*维护角色功能信息（先把原有的关系信息删除，然后再新建关系）begin*/
    if (role != null && funchoose != null && !"".equals(funchoose)) {
      roleFunctionDao.delete(role.getId());
      String[] funcids = funchoose.split(",");
      List<String> funidLis = Arrays.asList(funcids);
      for (String funcid : funidLis) {
        RoleFunction roleFunction = new RoleFunction();
        roleFunction.setFunctionid(Short.parseShort(funcid));
        roleFunction.setRoleid(role.getId());
        roleFunctionDao.insert(roleFunction);
      }
    }
        /*维护角色功能信息end*/
  }

  @Override
  public void delete(Short[] ids) {
    if (ids.length > 0) {
      List<Short> roleIds = Arrays.asList(ids);
      for (Short id : roleIds) {
        //删除角色信息的同时将关联表信息一并删除
        roleDao.delete(id);
        roleFunctionDao.delete(id);
        roleMenuDao.delete(id);
      }
    }
  }

  @Override
  public Role queryOne(Short id) {
    return roleDao.queryOne(id);
  }

  @Override
  public List<Role> query(Role role) {
    return roleDao.query(role);
  }

  @Override
  public List<Role> queryAll() {
    return roleDao.queryAll();
  }

  @Override
  public List<Role> getOperatorRole(Short id) {
    return roleDao.getOperatorRole(id);
  }
}
