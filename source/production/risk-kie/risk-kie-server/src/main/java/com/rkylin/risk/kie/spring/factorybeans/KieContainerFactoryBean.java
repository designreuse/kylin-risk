package com.rkylin.risk.kie.spring.factorybeans;

import com.rkylin.risk.kie.spring.KieContainerManager;
import com.rkylin.risk.kie.spring.sniffer.DefaultRuleReleaseIdBackendSniffer;
import com.rkylin.risk.kie.spring.sniffer.RuleReleaseIdBackendSniffer;
import com.rkylin.risk.kie.spring.sniffer.RuleReleaseIdCacheSniffer;
import com.rkylin.risk.kie.spring.sniffer.RuleReleaseIdFrontendSniffer;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.artifact.ArtifactUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.scanner.RiskKieRepositoryScannerImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;

/**
 * Created by tomalloc on 16-8-8.
 */
@Slf4j
public class KieContainerFactoryBean implements
    FactoryBean<KieContainerSession>,
    BeanFactoryPostProcessor, ApplicationListener<ContextRefreshedEvent>, DisposableBean {

  private final KieContainerSession kieContainerSession = new KieContainerSession();

  @Setter
  private Resource setting;

  @Setter
  private Set<ScannerReleaseId> scannerReleaseIdSet;

  @Setter
  private String releaseIdSniffer;

  private Map<ScannerReleaseId, KieScanner> enabledKieScannerMap = new LinkedHashMap<>();

  public KieContainerSession getObject() throws Exception {
    return kieContainerSession;
  }

  public Class<? extends KieContainer> getObjectType() {
    return KieContainer.class;
  }

  public boolean isSingleton() {
    return true;
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws
      BeansException {
    String path = null;
    try {
      if (setting != null) {
        path = setting.getFile().getAbsolutePath();
        log.info("load custom maven setting.xml at {}", path);
        System.setProperty("kie.maven.settings.custom", path);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    registerKieContainer(beanFactory);
  }

  private void registerKieContainer(
      ConfigurableListableBeanFactory configurableListableBeanFactory) {
    if (scannerReleaseIdSet == null) {
      return;
    }
    for (ScannerReleaseId scannerReleaseId : scannerReleaseIdSet) {
      ReleaseId releaseId = scannerReleaseId.getReleaseId();
      if (releaseId == null) {
        continue;
      }
      boolean scannerEnabled = scannerReleaseId.isScannerEnabled();
      log.info("start new KieContainer for {}", releaseId);
      KieContainer kContainer = KieServices.Factory.get().newKieContainer(releaseId);
      if (scannerEnabled) {
        KieScanner kieScanner = KieServices.Factory.get().newKieScanner(kContainer);
        String scannerId = scannerReleaseId.getScannerId();
        if (scannerId == null) {
          scannerId = releaseId.getGroupId() + "#" + releaseId.getArtifactId() + "#scanner";
        }
        configurableListableBeanFactory.registerSingleton(scannerId, kieScanner);
        enabledKieScannerMap.put(scannerReleaseId, kieScanner);
      }
      ReleaseId currentVersionReleaseId = kContainer.getReleaseId();
      log.info("group:{},artifactId:{} rule first registered version {}", releaseId.getGroupId(),
          releaseId.getArtifactId(), currentVersionReleaseId.getVersion());
      kieContainerSession.put(
          ArtifactUtils.versionlessKey(releaseId.getGroupId(), releaseId.getArtifactId()),
          kContainer);
    }
  }

  private RuleReleaseIdBackendSniffer getSniffer(ApplicationContext context) {
    if (releaseIdSniffer != null && !"".equals(releaseIdSniffer.trim())) {
      Object sniffer = null;
      try {
        sniffer = context.getBean(releaseIdSniffer);
      } catch (final Exception e) {
        log.warn("not found bean " + releaseIdSniffer, e);
      }
      if (sniffer != null) {
        RuleReleaseIdFrontendSniffer frontendSniffer = (RuleReleaseIdFrontendSniffer) sniffer;
        return new RuleReleaseIdCacheSniffer(frontendSniffer);
      }
    }
    return new DefaultRuleReleaseIdBackendSniffer();
  }

  @Override public void onApplicationEvent(ContextRefreshedEvent event) {
    log.info("startup kie scanner");
    final ApplicationContext context = event.getApplicationContext();
    if (context == null) {
      log.error("ApplicationContext is null");
      throw new RuntimeException("applicationContext is not null");
    }
    for (Map.Entry<ScannerReleaseId, KieScanner> entry : enabledKieScannerMap.entrySet()) {
      ScannerReleaseId scannerReleaseId = entry.getKey();
      KieScanner kieScanner = entry.getValue();
      long scannerInterval = scannerReleaseId.getScannerInterval();

      long delay = scannerInterval;
      if (kieScanner instanceof RiskKieRepositoryScannerImpl) {

        final RuleReleaseIdBackendSniffer backendSniffer = getSniffer(context);

        delay = scannerReleaseId.getDelay();
        //如果为自定义的KieScanner实现,通过回调来对KieContainerSession管理
        RiskKieRepositoryScannerImpl kieRepositoryScanner =
            (RiskKieRepositoryScannerImpl) kieScanner;
        kieRepositoryScanner.start(delay, scannerInterval,
            new KieContainerManager() {
              @Override public boolean scannerNewVersionReleaseId(ReleaseId releaseId) {
                return backendSniffer.scannerNewVersion(releaseId);
              }

              @Override public void putKieContainer(String key, ReleaseId newReleaseId) {
                KieContainer kieContainer =
                    KieServices.Factory.get().newKieContainer(newReleaseId);
                if (!kieContainerSession.contains(key)) {
                  kieContainerSession.put(key, kieContainer);
                } else {
                  kieContainerSession.replace(key, kieContainer);
                }
              }
            });
      } else {
        kieScanner.start(scannerInterval);
      }
      log.info("after {} milliseconds {} start scanning, each time interval of {} milliseconds",
          delay, scannerReleaseId.getReleaseId(), scannerInterval);
    }
  }

  @Override public void destroy() throws Exception {
    for (KieScanner kieScanner : enabledKieScannerMap.values()) {
      try {
        kieScanner.shutdown();
      } catch (Exception e) {
        log.error("close kieScanner error", e);
      }
    }
  }
}