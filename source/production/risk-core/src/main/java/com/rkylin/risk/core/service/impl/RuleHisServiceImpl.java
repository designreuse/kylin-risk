package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.RuleHisDao;
import com.rkylin.risk.core.entity.RuleHis;
import com.rkylin.risk.core.service.RuleHisService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by lina on 2016-6-23.
 */
@Service
public class RuleHisServiceImpl implements RuleHisService {
  @Resource
  private RuleHisDao ruleHisDao;

  @Override public RuleHis queryById(Short id) {
    return ruleHisDao.queryById(id);
  }

  @Override public List<RuleHis> queryByGroupVersionId(Short groupversionid) {
    return ruleHisDao.queryByGroupVersionId(groupversionid);
  }

  @Override public List<RuleHis> queryByRuleids(String ruleids) {
    return ruleHisDao.queryByRuleids(ruleids.split(","));
  }
}
