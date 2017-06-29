package com.rkylin.risk.kie.spring.factorybeans;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

/**
 * Created by tomalloc on 16-8-10.
 */
@Slf4j
public class KieContainerSession {
  /**
   * KieContainer容器 key是groupId:artifactId
   */
  private ConcurrentMap<String, KieContainer> kieContainerMap =
      new ConcurrentHashMap<String, KieContainer>();

  protected void replace(String name, KieContainer kieContainer) {
    ReleaseId oldReleasId = kieContainerMap.get(name).getReleaseId();
    log.info("replace from {}  to {}", oldReleasId, kieContainer.getReleaseId());
    kieContainerMap.replace(name, kieContainer);
  }

  protected boolean contains(String name) {
    return kieContainerMap.containsKey(name);
  }

  protected void put(String name, KieContainer kieContainer) {
    log.info("add {} to KieContainerSession", kieContainer.getReleaseId().toString());
    kieContainerMap.put(name, kieContainer);
  }

  public KieContainer getBean(String name) {
    return kieContainerMap.get(name);
  }
}
