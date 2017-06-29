package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Operator;
import com.rkylin.risk.core.entity.OperatorRole;
import java.util.List;

/**
 * Created by 201506290344 on 2015/8/14.
 */
public interface OperatorService {
  Operator insert(Operator operator);

  Operator queryOperatorByUsername(String username);

  Operator queryById(String id);

  Operator update(Operator operator, String roleIds,
                  String productId);

  Operator addNewOperator(Operator operator, String[] roleIds);

  List<Operator> queryAllOperator(Operator operator);

  Boolean deleteOpers(String ids);

  List<OperatorRole> queryByOperid(Short operid);

  Operator updatePassWord(Operator operator);

  Operator update(Operator operator);

  List<Operator> queryByOperatorIds(List list);
}