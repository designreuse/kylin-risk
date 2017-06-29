package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.core.service.GroupVersionService;
import com.rkylin.risk.kie.spring.sniffer.RuleReleaseIdFrontendSniffer;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by tomalloc on 16-9-2.
 */
@Component("ruleReleaseIdCacheSniffer")
public class RuleReleaseIdCacheSnifferBizImpl implements RuleReleaseIdFrontendSniffer {

  @Resource
  private GroupVersionService groupVersionService;

  @Override public String scannerNewVersion(String group, String artifactId) {
    String versionFromCache = groupVersionService.loadRuleGroupVersionFromCache(group, artifactId);
    if (StringUtils.isNotEmpty(versionFromCache)) {
      return versionFromCache;
    }
    return groupVersionService.queryRuleGroupVersion(group, artifactId);
  }
}
