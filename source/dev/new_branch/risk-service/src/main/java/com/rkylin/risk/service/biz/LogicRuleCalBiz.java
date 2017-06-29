package com.rkylin.risk.service.biz;

import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.dto.ResultBean;

/**
 * Created by lina on 2016-7-26.
 */
public interface LogicRuleCalBiz {
  /**
   * 个人系统准入要求审核规则-帮帮助学
   */
  ResultBean calPersionalRuleByBestudent(LogicRuleBean logic);

  /**
   * 个人系统准入要求审核规则-课栈网
   */
  ResultBean calPersionalRuleByKezhanwang(LogicRuleBean logicRuleBean);

  /**
   * 课栈机构数据系统监测规则
   */
  ResultBean calOrganLogicRuleKezhanwang(LogicRuleBean logic);
  /**
   * 帮帮助学机构数据系统监测规则
   */
  ResultBean calOrganLogicRuleBestudent(LogicRuleBean logic);

  /**
   * 全国集团类培训机构总部
   */
  ResultBean calGroupHeadLogicRule(LogicRuleBean logic);

  /**
   * 全国集团类培训机构分支机构
   */
  ResultBean calGroupBranchLogicRule(LogicRuleBean logic);

  /**
   * 大型机构总部
   */
  ResultBean calLargeHeadLogicRule(LogicRuleBean logic);

  /**
   * 大型机构分支机构
   */
  ResultBean calLargeBranchLogicRule(LogicRuleBean logic);

  /**
   * 中小型机构
   */
  ResultBean calmediumRule(LogicRuleBean logic);

}
