package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.RuleHisDao;
import com.rkylin.risk.core.entity.RuleHis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-6-23.
 */
@Repository
public class RuleHisDaoImpl extends BaseDaoImpl<RuleHis> implements RuleHisDao {
  @Override public RuleHis queryById(Short id) {
    return super.get(id);
  }

  @Override public RuleHis insert(RuleHis ruleHis) {
    super.add(ruleHis);
    return ruleHis;
  }

  @Override public void insertBatch(List<RuleHis> ruleHisList) {
    if (ruleHisList != null && !ruleHisList.isEmpty()) {
      super.addBatch("insertBatch", ruleHisList);
    }
  }

  @Override public List<RuleHis> queryByGroupVersionId(Short groupversionid) {
    return super.query("queryByGroupVersionId", groupversionid);
  }

  @Override public List<RuleHis> queryByRuleids(String[] ruleids) {
    Map rulehismap = new HashMap();
    rulehismap.put("ruleids", ruleids);
    return super.query("queryByRuleids", rulehismap);
  }
}
