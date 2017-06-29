package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.FactorDao;
import com.rkylin.risk.core.entity.Factor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508240185 on 2015/9/29.
 */
@Repository
public class FactorDaoImpl extends BaseDaoImpl<Factor> implements FactorDao {
  @Override
  public List<Factor> queryFactorByType(String riskType, String customertype) {
    Factor factor = new Factor();
    factor.setCustomertype(customertype);
    factor.setRisktype(riskType);
    return super.selectList("queryFactorByType", factor);
  }

  @Override
  public Factor addFactor(Factor factor) {
    super.add(factor);
    return factor;
  }

  @Override
  public Factor findById(int id) {
    return super.get(id);
  }

  @Override
  public Factor updateFactor(Factor factor) {
    super.modify(factor);
    return factor;
  }

  @Override
  public List<Factor> queryByParentid(int parentid) {
    return super.selectList("queryByParentid", parentid);
  }

  @Override
  public Factor findByCode(String code) {
    List<Factor> list = super.selectList("queryByCode", code);
    if (!list.isEmpty()) {
      return list.get(0);
    } else {
      return null;
    }
  }

  @Override
  public void deleteFactor(Integer id) {
    super.del(id);
  }
}
