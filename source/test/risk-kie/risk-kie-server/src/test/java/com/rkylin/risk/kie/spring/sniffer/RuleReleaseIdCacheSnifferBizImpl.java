package com.rkylin.risk.kie.spring.sniffer;

import javax.annotation.Resource;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tomalloc on 16-9-6.
 */
@Component("ruleReleaseIdCacheSniffer")
@Scope("singleton")
public class RuleReleaseIdCacheSnifferBizImpl implements RuleReleaseIdFrontendSniffer {

  @Resource
  private GroupVersionDao groupVersionDao;

  @Getter
  private boolean runSuccess;

  @Override public String scannerNewVersion(String group, String artifactId) {
    String newVersion = groupVersionDao.queryRuleGroupVersion(group, artifactId);
    runSuccess = true;
    return newVersion;
  }
}