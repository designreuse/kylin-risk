package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.entity.Rule;
import com.rkylin.risk.core.service.DictionaryService;
import com.rkylin.risk.core.service.RuleService;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeXml;

/**
 * Created by lina on 2016-5-5.
 */
@RestController
@RequestMapping("/api/1/rule")
public class RuleRestAction {
  @Resource
  private RuleService ruleService;
  @Resource
  private DictionaryService dictionaryService;

  /**
   * 添加规则
   */
  @RequestMapping("addRule")
  public Rule insert(@ModelAttribute Rule rule,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    unXSS(rule);
    ruleService.insert(rule, auth);
    return new Rule();
  }

  private void unXSS(Rule rule) {
    if (rule != null) {
      rule.setConditions(unescapeXml(rule.getConditions()));
      rule.setLogicsym(unescapeXml(rule.getLogicsym()));
    }
  }

  /**
   * 修改规则
   */
  @RequestMapping("modifyRule")
  public Rule modifyRule(@ModelAttribute Rule rule,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    unXSS(rule);
    ruleService.updateRule(rule, auth);
    return new Rule();
  }

  @RequestMapping("modifyRuleStatus")
  public Rule modifyRuleStatus(@RequestParam String id, String status, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    ruleService.updateRuleStatus(id, status, auth);
    return new Rule();
  }

  @RequestMapping("deleteRule")
  public Rule deleteRule(@RequestParam String id) {
    ruleService.deleteRules(id);
    return new Rule();
  }

  @RequestMapping("queryDicByDictcode")
  public List<DictionaryCode> queryDicByDictcode() {
    return dictionaryService.queryByDictCode("ruleparam");
  }
}
