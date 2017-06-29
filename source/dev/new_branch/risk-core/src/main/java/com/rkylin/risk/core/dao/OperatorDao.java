package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Operator;

import java.util.List;

/**
 * Created by 201506290344 on 2015/8/14.
 */
public interface OperatorDao {
  Operator insert(Operator operator);

  Operator queryOperatorByUsername(String username);

  Operator update(Operator operator);

  List<Operator> queryOperators(Operator operator);

  Integer deleteOper(Short id);

  Operator queryById(Short id);

  Operator updatePassWord(Operator operator);

  List<Operator> queryByOperatorIds(List list);
}
