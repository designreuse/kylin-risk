package com.rkylin.risk.boss.biz.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.boss.biz.RuleBiz;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.RuleHisDao;
import com.rkylin.risk.core.dto.RuleBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.GroupVersion;
import com.rkylin.risk.core.entity.RuleHis;
import com.rkylin.risk.core.service.GroupVersionService;
import com.rkylin.risk.rule.vm.BuildRule;
import com.rkylin.risk.rule.vm.Configration;
import com.rkylin.risk.rule.vm.model.Artifact;
import com.rkylin.risk.rule.vm.model.ModuleInfo;
import com.rkylin.risk.rule.vm.model.RepositoryInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by lina on 2016-6-15.
 */
@Component("ruleBiz")
public class RuleBizImpl implements RuleBiz {
  @Resource
  private RuleHisDao ruleHisDao;
  @Resource
  private GroupVersionService groupVersionService;

  @Value("${maven.distribution.url}")
  private String mavenDistributionUrl;

  @Value("${maven.userName}")
  private String mavenUserName;

  @Value("${maven.password}")
  private String mavenPassword;

  private RepositoryInfo repositoryInfo;

  @PostConstruct
  public void init() {
    Objects.requireNonNull(mavenDistributionUrl);
    Objects.requireNonNull(mavenUserName);
    Objects.requireNonNull(mavenPassword);
    repositoryInfo = new RepositoryInfo(mavenUserName, mavenPassword);
  }

  @Override public void creategeneratingRule(GroupVersion groupVersion, Authorization auth) {
    ModuleInfo info = new ModuleInfo();
    Artifact artifact = new Artifact();
    artifact.setArtifactId(groupVersion.getIssueartifactid());
    artifact.setGroupId(groupVersion.getIssuegroupid());
    artifact.setVersion(groupVersion.getVersion());
    artifact.setDistributionUrl(mavenDistributionUrl);

    info.setArtifact(artifact);
    info.setRepositoryInfo(repositoryInfo);
    setTemplateRulePath(info, groupVersion);

    Map<String, Object> map = new HashMap<>();
    map.put("factors", setRuleBeanDetail(groupVersion));
    map.put("ruleBean", new RuleBean());
    info.setRuleData(map);

    Configration configration = new Configration(info);
    BuildRule droolsrule = new BuildRule(configration);
    try {
      droolsrule.deploy();
    } finally {
      droolsrule.clean();
    }
    groupVersion.setIsissue(Constants.INACTIVE);
    groupVersion.setIssueoperid(auth.getUserId());
    groupVersion.setIssueopername(auth.getRealname());
    groupVersionService.update(groupVersion);
  }

  public List<RuleBean> setRuleBeanDetail(GroupVersion groupVersion) {
    List<RuleHis> rules = ruleHisDao.queryByGroupVersionId(groupVersion.getId());
    List<RuleBean> ruleBeans = Lists.newArrayList();
    for (RuleHis rule : rules) {
      if (!StringUtils.isEmpty(rule.getFields())) {
        RuleBean ruleBean = new RuleBean();
        ruleBean.setConditions(makeUpString(rule.getConditions()));
        ruleBean.setConditionvals(makeUpStringAdd(rule.getConditionvals()));
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

  public String[] makeUpStringAdd(String str) {
    if (str != null) {
      String[] strArr = str.split(",");
      if (str.endsWith(",")) {
        strArr = (String[]) ArrayUtils.add(strArr, "");
      }
      return strArr;
    }
    return new String[0];
  }

  public String[] makeUpString(String str) {
    if (str != null) {
      String strTem = str.endsWith(",") ? str + " " : str;
      return strTem.split(",");
    } else {
      return new String[0];
    }
  }

  private void setTemplateRulePath(ModuleInfo info, GroupVersion groupVersion) {
    String path = "template/rule/";
    if ("logicrule".equals(groupVersion.getGrouptype())) {
      path += "logicRule";
    } else {
      path += "scoreRule";
    }
    info.setTemplateRulePath(path);
  }
}
