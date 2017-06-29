package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.OperatorRole;

import java.util.List;

/**
 * Created by 201508031790 on 2015/8/20.
 */
public interface OperatorRoleDao {
  OperatorRole insert(OperatorRole operatorRole);

  List<OperatorRole> queryByOperid(Short id);

  void delOperRole(Short id);

  void delByOperId(Short operId);
}
