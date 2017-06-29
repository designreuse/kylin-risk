package com.rkylin.risk.core.service;

import com.rkylin.risk.core.dto.ChannelsBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Factor;

import java.util.List;

/**
 * Created by 201508240185 on 2015/9/29.
 */
public interface FactorService {
  List<Factor> queryFactorByType(String riskType, String customertype);

  Factor addFactor(Factor factor, String riskType, String custType, Authorization auth,
      String addType, ChannelsBean channels);

  Factor modifyFactor(Factor factor, Authorization auth, String beforeWeight, String addType,
      ChannelsBean channels);

  Factor findById(int id);

  Factor updateFactor(Factor factor);

  String findFactorInTree(String riskType, String customertype);

  Factor findByCode(String code);

  /**
   * 删除风险因子节点
   */
  void deleteFactor(Integer id);
}
