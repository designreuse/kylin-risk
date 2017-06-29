package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Risklevel;

import java.util.List;

/**
 * Created by 201508240185 on 2015/9/11.
 */
public interface RiskLevelService {
  /**
   * 根据风控类型和客户类型查找风险级别
   */
  List<Risklevel> queryByRiskAndCustType(String riskType, String customType);

  Risklevel update(Risklevel level);

  Risklevel insert(Risklevel level);

  List<Risklevel> insertOrUpdateRiskGrade(List<Risklevel> levels, Authorization auth,
      String operType, String riskType, String customerType);
}