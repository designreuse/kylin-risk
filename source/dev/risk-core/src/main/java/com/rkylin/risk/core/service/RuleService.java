package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Rule;
import java.util.List;

/**
 * Created by lina on 2016-5-4. 规则管理
 */
public interface RuleService {

  /**
   * 添加规则
   */
  void insert(Rule rule, Authorization auth);

  /**
   * 修改规则
   */
  void updateRule(Rule rule,
      Authorization auth);

  void updateRuleStatus(String id, String status, Authorization auth);

  void deleteRules(String ids);

  Rule queryById(Short id);

  String createRulecode();

  List<Rule> queryRuleProByPro(String productid, String type);
}
