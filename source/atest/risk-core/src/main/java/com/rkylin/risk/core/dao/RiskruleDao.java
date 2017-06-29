package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Riskrule;
import java.util.List;

/**
 * Created by 201507270241 on 2015/9/8.
 */
public interface RiskruleDao {

  List<Riskrule> queryAll(Riskrule riskrule);

  Riskrule queryOne(Integer id);

  Riskrule insert(Riskrule riskrule);

  int update(Riskrule riskrule);

  void modifyStatus(Riskrule riskrules);

  void delete(Integer id);
}
