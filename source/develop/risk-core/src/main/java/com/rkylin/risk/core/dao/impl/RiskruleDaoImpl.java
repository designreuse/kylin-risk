package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.RiskruleDao;
import com.rkylin.risk.core.entity.Riskrule;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201507270241 on 2015/9/8.
 */
@Repository("riskruleDao")
public class RiskruleDaoImpl extends BaseDaoImpl<Riskrule> implements RiskruleDao {
  @Override
  public List<Riskrule> queryAll(Riskrule riskrule) {
    return super.query("queryAll", riskrule);
  }

  @Override
  public Riskrule queryOne(Integer id) {
    return super.queryOne(id);
  }

  @Override
  public Riskrule insert(Riskrule riskrule) {
    super.add(riskrule);
    return riskrule;
  }

  @Override
  public int update(Riskrule riskrule) {
    return super.modify(riskrule);
  }

  @Override
  public void modifyStatus(Riskrule riskrule) {
    super.modify("modifyStatus", riskrule);
  }

  @Override
  public void delete(Integer id) {
    super.del(id);
  }
}
