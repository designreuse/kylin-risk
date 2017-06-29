package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.RuleHis;
import com.rkylin.risk.core.service.RuleHisService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lina on 2016-7-22.
 */
@RestController
@RequestMapping("/api/1/ruleHis")
public class RuleHisRestAction {
  @Resource
  private RuleHisService ruleHisService;

  /**
   * 查询历史规则
   */
  @RequestMapping("queryRuleHis")
  public List<RuleHis> queryRuleHis(@RequestParam String groupid, String ruleids, String restr) {
    if ("groupVersion/toQueryGroupVersion".endsWith(restr)) {
      return ruleHisService.queryByGroupVersionId(Short.parseShort(groupid));
    } else {
      return ruleHisService.queryByRuleids(ruleids);
    }
  }
}
