package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.RuleHis;
import java.util.List;

/**
 * Created by lina on 2016-6-23.
 */
public interface RuleHisService {
  RuleHis queryById(Short id);
  List<RuleHis> queryByGroupVersionId(Short groupversionid);
  List<RuleHis> queryByRuleids(String ruleids);
}
