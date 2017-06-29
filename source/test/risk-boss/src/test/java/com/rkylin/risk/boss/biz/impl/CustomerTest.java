package com.rkylin.risk.boss.biz.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.core.dao.RuleHisDao;
import com.rkylin.risk.core.dto.RuleBean;
import com.rkylin.risk.core.entity.GroupVersion;
import com.rkylin.risk.core.entity.RuleHis;
import com.rkylin.risk.rule.vm.BuildRule;
import com.rkylin.risk.rule.vm.Configration;
import com.rkylin.risk.rule.vm.model.Artifact;
import com.rkylin.risk.rule.vm.model.ModuleInfo;
import com.rkylin.risk.rule.vm.model.RepositoryInfo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tomalloc on 16-3-9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class CustomerTest {
  @Resource
  private RuleHisDao ruleHisDao;

  @Test
  public void deployTest() {
    ModuleInfo info = new ModuleInfo();
    Artifact artifact = new Artifact();
    artifact.setArtifactId("logicRule");
    artifact.setGroupId("com.risk.rule");
    artifact.setVersion("1");
    artifact.setDistributionUrl("http://localhost:8081/artifactory/rule");
    info.setArtifact(artifact);
    info.setTemplateRulePath("template/rule/logicRule");
    info.setRepositoryInfo(new RepositoryInfo("admin", "password"));
    Map<String, Object> map = new HashMap<String, Object>();
    GroupVersion groupVersion = new GroupVersion();
    groupVersion.setId(new Short("13"));
    map.put("factors", setRuleBeanDetail(groupVersion));
    map.put("ruleBean", new RuleBean());
    info.setRuleData(map);
    Configration configration = new Configration(info);
    BuildRule rule = new BuildRule(configration);
    rule.deploy();
  }

  public List<RuleBean> setRuleBeanDetail(GroupVersion groupVersion) {
    List<RuleHis> rules = ruleHisDao.queryByGroupVersionId(groupVersion.getId());
    List<RuleBean> ruleBeans = Lists.newArrayList();
    for (RuleHis rule : rules) {
      if (!StringUtils.isEmpty(rule.getFields())) {
        RuleBean ruleBean = new RuleBean();
        ruleBean.setConditions(makeUpString(rule.getConditions()));
        ruleBean.setConditionvals(makeUpString(rule.getConditionvals()));
        ruleBean.setLogicsym(makeUpString(rule.getLogicsym()));
        ruleBean.setFields(rule.getFields() == null ? null : rule.getFields().split(","));
        ruleBean.setResult(rule.getResult());
        ruleBean.setPriority(rule.getPriority());
        ruleBean.setRulename(rule.getRulename());
        ruleBean.setRulehisid(rule.getId().toString());
        ruleBeans.add(ruleBean);
      }
    }
    return ruleBeans;
  }

  public String[] makeUpString(String str) {
    if (str != null) {
      String strTem = str.endsWith(",") ? str + " " : str;
      return strTem.split(",");
    } else {
      return null;
    }
  }

  @Test
  public void testFoo() {
    String str = "中国,==,==,==,,==,==";

    System.out.println(Arrays.toString(str.split(",")));
    StringTokenizer tokenizer = new StringTokenizer(str, ",");
    System.out.println(tokenizer.countTokens());
  }
}
