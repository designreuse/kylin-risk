package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.RuleDao;
import com.rkylin.risk.core.entity.Rule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-5-4.
 */
@Repository("ruleDao")
public class RuleDaoImpl extends BaseDaoImpl<Rule> implements RuleDao {
  @Override public Rule insert(Rule rule) {
    super.add(rule);
    return rule;
  }

  @Override public Rule queryById(Short id) {
    return super.get(id);
  }

  @Override public void update(Rule rule) {
    super.modify(rule);
  }

  @Override public void delete(Short id) {
    super.del(id);
  }

  @Override public String queryMaxRulecode() {
    return super.queryOne("queryMaxRulecode");
  }

  @Override public List<Rule> queryRuleProByPro(String productid, String type) {
    Map map = new HashMap();
    map.put("productid", productid);
    map.put("type", type);
    return super.query("queryRuleProByPro", map);
  }

  @Override public List<Rule> queryByGroupidAndSta(Short groupid, String status) {
    Map map = new HashMap();
    map.put("groupid", groupid);
    map.put("status", status);
    return super.query("queryByGroupidAndSta", map);
  }
}
