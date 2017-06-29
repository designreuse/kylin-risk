package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.RiskGradeCust;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 201508240185 on 2015/9/16.
 */
public interface RiskGradeCustService {
  RiskGradeCust create(RiskGradeCust grade);

  List<HashMap> queryPayGradeCust(Map<String, Object> map);

  RiskGradeCust queryById(Integer id);

  RiskGradeCust update(RiskGradeCust grade);

  boolean updateRiskGradeList(String ids, String updateGrade, String reason, Authorization auth);

  boolean updateRiskStatusList(String ids, String status, Authorization auth);

  RiskGradeCust queryByCustRisk(long custid, String riskType);
}