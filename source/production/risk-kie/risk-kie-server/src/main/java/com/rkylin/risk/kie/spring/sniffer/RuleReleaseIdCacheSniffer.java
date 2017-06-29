package com.rkylin.risk.kie.spring.sniffer;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.kie.api.builder.ReleaseId;

/**
 * Created by tomalloc on 16-9-1.
 */
@Slf4j
public class RuleReleaseIdCacheSniffer implements RuleReleaseIdBackendSniffer {

  private RuleReleaseIdFrontendSniffer frontendSniffer;

  public RuleReleaseIdCacheSniffer(RuleReleaseIdFrontendSniffer frontendSniffer) {
    this.frontendSniffer = frontendSniffer;
  }

  @Override public boolean scannerNewVersion(ReleaseId releaseId) {
    String group = releaseId.getGroupId();
    String artifactId = releaseId.getArtifactId();
    String scannerVersion = frontendSniffer.scannerNewVersion(group, artifactId);
    if (scannerVersion == null || scannerVersion.trim().equals("")) {
      log.warn("{} can't find any release version",releaseId);
      return false;
    }
    String oldVersion = releaseId.getVersion();

    int compareResult =
        new ComparableVersion(scannerVersion).compareTo(new ComparableVersion(oldVersion));
    if (compareResult > 0) {
      return true;
    }
    if (compareResult == 0) {
      return ArtifactUtils.isSnapshot(scannerVersion);
    }
    return false;
  }
}
