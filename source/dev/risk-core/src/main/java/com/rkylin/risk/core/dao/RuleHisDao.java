package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.RuleHis;
import java.util.List;

/**
 * Created by lina on 2016-6-23.
 */
public interface RuleHisDao {
  RuleHis queryById(Short id);

  RuleHis insert(RuleHis ruleHis);

  void insertBatch(List<RuleHis> ruleHisList);

  List<RuleHis> queryByGroupVersionId(Short groupversionid);

  List<RuleHis> queryByRuleids(String[] ruleids);
}
