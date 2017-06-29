package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.RoleMenuDao;
import com.rkylin.risk.core.entity.RoleMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/21.
 */
@Repository("roleMenuDao")
public class RoleMenuDaoImpl extends BaseDaoImpl<RoleMenu> implements RoleMenuDao {
  @Override
  public List<RoleMenu> queryAll(Short roleid) {
    return super.query("queryAll", roleid);
  }

  @Override
  public void delete(Short roleid) {
    super.del(roleid);
  }

  @Override
  public void insert(RoleMenu roleMenu) {
    super.add(roleMenu);
  }

  @Override
  public List<RoleMenu> queryByMenuid(Short menuid) {
    return super.query("queryByMenuid", menuid);
  }

  @Override
  public void deleteRolemenuByMenuid(Short menuid) {
    super.del("deleteRolemenuByMenuid", menuid);
  }
}
