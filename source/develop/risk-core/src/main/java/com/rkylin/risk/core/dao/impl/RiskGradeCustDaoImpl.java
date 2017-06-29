package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.RiskGradeCustDao;
import com.rkylin.risk.core.entity.RiskGradeCust;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 201508240185 on 2015/9/16.
 */
@Repository
public class RiskGradeCustDaoImpl extends BaseDaoImpl<RiskGradeCust> implements RiskGradeCustDao {
  @Override
  public RiskGradeCust create(RiskGradeCust grade) {
    super.add(grade);
    return grade;
  }

  @Override
  public List queryPayGradeCust(Map<String, Object> map) {
    return super.query("queryPayGradeCust", map);
  }

  @Override
  public RiskGradeCust update(RiskGradeCust grade) {
    super.modify(grade);
    return grade;
  }

  @Override
  public RiskGradeCust queryById(Integer id) {
    return super.get(id);
  }

  @Override
  public RiskGradeCust queryByCustRisk(long custid, String riskType) {
    Map map = new HashMap();
    map.put("custid", custid);
    map.put("riskType", riskType);
    List<RiskGradeCust> list = super.query("queryByCustRisk", map);
    if (list == null || list.isEmpty()) {
      return null;
    } else {
      return list.get(0);
    }
  }
}
