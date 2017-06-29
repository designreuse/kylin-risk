package com.rkylin.risk.rule.vm;

import com.rkylin.risk.rule.vm.model.Artifact;
import com.rkylin.risk.rule.vm.model.ModuleInfo;
import com.rkylin.risk.rule.vm.model.RepositoryInfo;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-3-9.
 */
public class BuildRuleTest {

  private Configration make(String templatePath) {
    ModuleInfo info = new ModuleInfo();
    Artifact artifact = new Artifact();
    artifact.setArtifactId("order");
    artifact.setGroupId("com.risk.rule");
    artifact.setVersion("1.0-SNAPSHOT");
    artifact.setDistributionUrl("http://localhost:8081/artifactory/rule");
    info.setArtifact(artifact);
    info.setTemplateRulePath(templatePath);
    info.setRepositoryInfo(new RepositoryInfo("admin", "password"));
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("name", "drl");
    map.put("isTest", true);
    info.setRuleData(map);
    Configration configration = new Configration(info);
    BuildRule rule = new BuildRule(configration);
    rule.makePackage();
    return configration;
  }

  @Test
  public void packageRuleTest() {
    make("template/rule/rule1");
  }

  @Test
  public void packageVMTest() {
    Configration configration = make("template/rule/rule-vm");
    Path buildHome = configration.getBuildHome();
    Path rulePath = buildHome.resolve("target/classes").resolve("test.drl");
    assertThat(Files.exists(rulePath)).isTrue();
  }
}
