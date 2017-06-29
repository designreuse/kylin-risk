package com.rkylin.risk.kie.spring.factorybeans;

import com.rkylin.risk.kie.spring.sniffer.RuleReleaseIdCacheSnifferBizImpl;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Created by tomalloc on 16-8-10.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-test.xml"})
public class KieContainerFactoryBeanTest {
  @Resource
  KieContainerSession kieContainerSession;

  @Resource
  private RuleReleaseIdCacheSnifferBizImpl ruleReleaseIdCacheSniffer;

  @Test
  public void beanTest() {
    KieContainer kieContainer =
        kieContainerSession.getBean("com.risk.kie.rule:kie-server-rule-test");
    KieSession kieSession = kieContainer.newKieSession();
    kieSession.fireAllRules();
    kieSession.destroy();
    assertThat(kieSession).isNotNull();
  }
  @Test
  public void scannerTest() throws InterruptedException {
    int i=0;
    while (i<3){
      if(ruleReleaseIdCacheSniffer.isRunSuccess()){
        return;
      }
      i++;
      TimeUnit.SECONDS.sleep(20);
    }
    fail("scanner fail");
  }
}
