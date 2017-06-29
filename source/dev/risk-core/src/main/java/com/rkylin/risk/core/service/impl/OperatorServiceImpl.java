package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.OperatorDao;
import com.rkylin.risk.core.dao.OperatorRoleDao;
import com.rkylin.risk.core.entity.Operator;
import com.rkylin.risk.core.entity.OperatorRole;
import com.rkylin.risk.core.service.OperatorService;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by 201506290344 on 2015/8/14.
 */
@Slf4j
@Service("operatorService")
public class OperatorServiceImpl implements OperatorService {
  @Resource
  private OperatorDao operatorDao;
  @Resource
  private OperatorRoleDao operatorRoleDao;

  @Override
  public Operator insert(Operator operator) {
    return operatorDao.insert(operator);
  }

  @Override
  public Operator queryOperatorByUsername(String username) {
    return operatorDao.queryOperatorByUsername(username);
  }

  @Override
  public Operator update(Operator operator) {
    return operatorDao.update(operator);
  }

  @Override
  public Operator update(Operator operator, String roleIds,
      String productId) {
    operatorRoleDao.delByOperId(operator.getId());
    String[] roleArray = roleIds.split(",");
    //插入操作员角色关系表
    insetOperatorRole(roleArray, operator);
    operator.setProducts(productId.startsWith(",") ? productId.substring(1) : productId);
    operator = operatorDao.update(operator);
    return operator;
  }

  //插入操作员角色关系表
  public void insetOperatorRole(String[] roleArray, Operator operator) {
    for (int i = 0; i < roleArray.length; i++) {
      OperatorRole operrole = new OperatorRole();
      operrole.setOperatorid(operator.getId());
      operrole.setRoleid(Short.valueOf(roleArray[i]));
      operatorRoleDao.insert(operrole);
    }
  }

  @Override
  public Operator addNewOperator(Operator operator, String[] roleIds) {
    Operator operator1 = operatorDao.insert(operator);
    insetOperatorRole(roleIds, operator);
    return operator1;
  }

  @Override
  public List<Operator> queryAllOperator(Operator operator) {
    return operatorDao.queryOperators(operator);
  }

  @Override
  public Boolean deleteOpers(String ids) {
    String[] idsarray = ids.split(",");
    try {
      for (int i = 0; i < idsarray.length; i++) {
        List<OperatorRole> list = operatorRoleDao.queryByOperid(Short.valueOf(idsarray[i]));
        if (!list.isEmpty()) {
          for (int j = 0; j < list.size(); j++) {
            operatorRoleDao.delOperRole(list.get(j).getId());
          }
        }
        operatorDao.deleteOper(Short.valueOf(idsarray[i]));
      }
      return true;
    } catch (Exception e) {
      log.info(e.getMessage(), e);
      return false;
    }
  }

  @Override
  public Operator queryById(String id) {
    return operatorDao.queryById(Short.valueOf(id));
  }

  @Override
  public List<OperatorRole> queryByOperid(Short operid) {
    return operatorRoleDao.queryByOperid(operid);
  }

  @Override
  public Operator updatePassWord(Operator operator) {
    return operatorDao.updatePassWord(operator);
  }

  @Override
  public List<Operator> queryByOperatorIds(List list) {
    return operatorDao.queryByOperatorIds(list);
  }
}
