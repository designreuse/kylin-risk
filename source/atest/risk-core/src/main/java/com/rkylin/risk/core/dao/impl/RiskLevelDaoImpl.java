package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.RiskLevelDao;
import com.rkylin.risk.core.entity.Risklevel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508240185 on 2015/9/11.
 */
@Repository("riskLevelDao")
public class RiskLevelDaoImpl extends BaseDaoImpl<Risklevel> implements RiskLevelDao {
  @Override
  public Risklevel insert(Risklevel level) {
    super.add(level);
    return level;
  }

  @Override
  public Risklevel update(Risklevel level) {
    super.modify(level);
    return level;
  }

  @Override
  public List<Risklevel> queryByRiskAndCustType(String riskType, String customType) {
    Risklevel level = new Risklevel();
    level.setCustomertype(customType);
    level.setRisktype(riskType);
    return super.selectList("queryByRiskAndCustType", level);
  }
}
