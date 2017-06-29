package com.rkylin.risk.kie.spring.sniffer;

/**
 * Created by tomalloc on 16-9-1.
 */
public interface RuleReleaseIdFrontendSniffer {
  String scannerNewVersion(String group,String artifactId);
}
