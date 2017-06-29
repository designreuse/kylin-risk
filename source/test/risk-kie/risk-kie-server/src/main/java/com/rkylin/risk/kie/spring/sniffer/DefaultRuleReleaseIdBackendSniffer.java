package com.rkylin.risk.kie.spring.sniffer;

import org.kie.api.builder.ReleaseId;

/**
 * Created by tomalloc on 16-9-8.
 */
public class DefaultRuleReleaseIdBackendSniffer implements RuleReleaseIdBackendSniffer {
  @Override public boolean scannerNewVersion(ReleaseId releaseId) {
    return true;
  }
}
