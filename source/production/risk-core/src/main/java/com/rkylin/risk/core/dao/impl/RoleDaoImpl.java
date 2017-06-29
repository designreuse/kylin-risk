package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.RoleDao;
import com.rkylin.risk.core.entity.Role;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by 201507270241 on 2015/8/19.
 */
@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

  @Override
  public Role insert(Role role) {
    super.add(role);
    return role;
  }

  @Override
  public void update(Role role) {
    super.modify(role);
  }

  @Override
  public void delete(Short id) {
    super.del(id);
  }

  @Override
  public Role queryOne(Short id) {
    return super.queryOne(id);
  }

  @Override
  public List<Role> query(Role role) {
    return super.query("query", role);
  }

  @Override
  public List<Role> queryAll() {
    return super.queryAllList();
  }

  @Override
  public List<Role> getOperatorRole(Short id) {
    return super.query("getOperatorRole", id);
  }
}
