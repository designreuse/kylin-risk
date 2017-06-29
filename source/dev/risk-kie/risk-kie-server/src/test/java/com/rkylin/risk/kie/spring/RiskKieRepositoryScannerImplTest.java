package com.rkylin.risk.kie.spring;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.scanner.RiskKieRepositoryScannerImpl;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-8-10.
 */
@Slf4j
public class RiskKieRepositoryScannerImplTest {
  @BeforeClass
  public static void beforeClass() {
    Resource setting = new ClassPathResource("risk-settings.xml");
    String path = null;
    try {
      path = setting.getFile().getAbsolutePath();
      System.setProperty("kie.maven.settings.custom", path);
      log.info("load maven setting {}", path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void loadRiskKieRepositoryScannerImplTest() {
    ReleaseId releaseId = new ReleaseIdImpl("com.risk.rule.develop.P000008", "P00000514", "15");
    KieContainer kContainer = KieServices.Factory.get().newKieContainer(releaseId);
    KieScanner kieScanner = KieServices.Factory.get().newKieScanner(kContainer);
    log.info("scanner type={}", kieScanner.getClass().getCanonicalName());
    assertThat(kieScanner).isInstanceOf(RiskKieRepositoryScannerImpl.class);
  }
}
