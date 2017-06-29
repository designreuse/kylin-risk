package com.rkylin.risk.kie.spring;

import org.kie.api.builder.ReleaseId;

/**
 * KieContainer容器管理 Created by tomalloc on 16-8-10.
 */
public interface KieContainerManager {

  boolean scannerNewVersionReleaseId(ReleaseId releaseId);

  /**
   * 添加KieContainer
   */
  void putKieContainer(String key, ReleaseId releaseId);
}
