package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Factor;

import java.util.List;

/**
 * Created by 201508240185 on 2015/9/29.
 */
public interface FactorDao {
  List<Factor> queryFactorByType(String riskType, String customertype);

  Factor addFactor(Factor factor);

  Factor findById(int id);

  Factor updateFactor(Factor factor);

  List<Factor> queryByParentid(int parentid);

  Factor findByCode(String code);

  /**
   * 删除风险因子节点
   */
  void deleteFactor(Integer id);
}
