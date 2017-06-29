package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.RoleFunctionDao;
import com.rkylin.risk.core.entity.RoleFunction;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/21.
 */
@Repository("roleFunctionDao")
public class RoleFunctionDaoImpl extends BaseDaoImpl<RoleFunction> implements RoleFunctionDao {
  @Override
  public List<RoleFunction> queryAll(Short roleid) {
    return super.selectList("queryAll", roleid);
  }

  @Override
  public void delete(Short roleid) {
    super.del(roleid);
  }

  @Override
  public void insert(RoleFunction roleFunction) {
    super.add(roleFunction);
  }
}
