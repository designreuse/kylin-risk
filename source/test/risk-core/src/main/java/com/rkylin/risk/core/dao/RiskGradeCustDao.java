package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.RiskGradeCust;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 201508240185 on 2015/9/16.
 */
public interface RiskGradeCustDao {
  RiskGradeCust create(RiskGradeCust grade);

  List<HashMap> queryPayGradeCust(Map<String, Object> map);

  RiskGradeCust queryById(Integer id);

  RiskGradeCust update(RiskGradeCust grade);

  RiskGradeCust queryByCustRisk(long custid, String riskType);
}
