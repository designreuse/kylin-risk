package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.OperatorRoleDao;
import com.rkylin.risk.core.entity.OperatorRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508031790 on 2015/8/20.
 */
@Repository("operatorRoleDao")
public class OperatorRoleDaoImpl extends BaseDaoImpl<OperatorRole> implements OperatorRoleDao {
  @Override
  public OperatorRole insert(OperatorRole operatorrole) {
    super.add(operatorrole);
    return operatorrole;
  }

  @Override
  public List<OperatorRole> queryByOperid(Short id) {
    List<OperatorRole> list = super.query("queryByOperid", id);
    return list;
  }

  @Override
  public void delOperRole(Short id) {
    super.del(id);
  }

  @Override
  public void delByOperId(Short operId) {
    super.del("delByOperId", operId);
  }
}
