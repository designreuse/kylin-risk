package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.GroupDao;
import com.rkylin.risk.core.dao.RuleDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Group;
import com.rkylin.risk.core.entity.Rule;
import com.rkylin.risk.core.service.RuleService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by lina on 2016-5-4.
 */
@Service
public class RuleServiceImpl implements RuleService {
  @Resource
  private RuleDao ruleDao;
  @Resource
  private GroupDao groupDao;

  @Override public void insert(Rule rule,
      Authorization auth) {
    rule.setRulecode(createRulecode());
    rule.setCreateoperid(auth.getUserId());
    rule.setCreateopername(auth.getRealname());
    rule.setConditions(rule.getConditions() == null ? null
        : rule.getConditions().replace("&gt;", ">").replace("&lt;", "<"));
    rule.setLogicsym(rule.getLogicsym() == null ? null : rule.getLogicsym().replace("&amp;", "&"));
    ruleDao.insert(rule);

    Group group = new Group();
    group.setId(rule.getGroupid());
    group.setIsexecute(Constants.ACTIVE);
    groupDao.update(group);
  }

  @Override public String createRulecode() {
    String rulecode = Constants.RULE_CODE_FLAG;
    String maxcode = ruleDao.queryMaxRulecode();
    if (StringUtils.isEmpty(maxcode)) {
      rulecode += "00001";
    } else {
      String maxIndex = StringUtils.stripStart(maxcode, Constants.RULE_CODE_FLAG);
      int codeIndex = Integer.parseInt(maxIndex) + 1;
      rulecode += String.format("%05d", codeIndex);
    }
    return rulecode;
  }

  @Override public void updateRule(Rule rule,
      Authorization auth) {
    Group group = new Group();
    group.setId(rule.getGroupid());
    group.setIsexecute(Constants.ACTIVE);
    groupDao.update(group);

    rule.setConditions(rule.getConditions() == null ? null
        : rule.getConditions().replace("&gt;", ">").replace("&lt;", "<"));
    rule.setLogicsym(rule.getLogicsym() == null ? null : rule.getLogicsym().replace("&amp;", "&"));
    ruleDao.update(rule);
  }

  @Override public void updateRuleStatus(String ids, String status, Authorization auth) {
    if (ids != null) {
      Group group = new Group();
      String[] idArr = ids.split(",");
      for (String id : idArr) {
        Rule rule = ruleDao.queryById(Short.parseShort(id));
        group.setId(rule.getGroupid());
        rule.setStatus("true".equals(status) ? Constants.ACTIVE : Constants.INACTIVE);
        ruleDao.update(rule);
      }
      group.setIsexecute(Constants.ACTIVE);
      groupDao.update(group);
    }
  }

  @Override public void deleteRules(String ids) {
    if (ids != null) {
      String[] idArr = ids.split(",");
      for (String id : idArr) {
        Short idTemp = Short.parseShort(id);
        ruleDao.delete(idTemp);
      }
    }
  }

  @Override public Rule queryById(Short id) {
    return ruleDao.queryById(id);
  }

  @Override public List<Rule> queryRuleProByPro(String productid, String type) {
    return ruleDao.queryRuleProByPro(productid, type);
  }
}
