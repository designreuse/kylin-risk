package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Rule;
import java.util.List;

/**
 * Created by lina on 2016-5-4. 规则管理
 */
public interface RuleDao {
  Rule insert(Rule rule);

  Rule queryById(Short id);

  void update(Rule rule);

  void delete(Short id);

  String queryMaxRulecode();

  List<Rule> queryRuleProByPro(String productid, String type);

  List<Rule> queryByGroupidAndSta(Short groupid, String status);
}
