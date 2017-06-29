package com.rkylin.risk.rule.vm;

import com.rkylin.risk.rule.vm.interpreter.Interpreter;
import com.rkylin.risk.rule.vm.interpreter.InterpreterFactory;
import com.rkylin.risk.rule.vm.model.ModuleInfo;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import static com.rkylin.risk.rule.vm.Consts.BUILD_KMODULE_XML;
import static com.rkylin.risk.rule.vm.Consts.BUILD_POM;
import static com.rkylin.risk.rule.vm.Consts.BUILD_SETTINGS;
import static com.rkylin.risk.rule.vm.Consts.BUILD_TEMPLATE_POM;
import static com.rkylin.risk.rule.vm.Consts.BUILD_TEMPLATE_SETTINGS;
import static com.rkylin.risk.rule.vm.Consts.KMODULE_DIRECTORY;
import static com.rkylin.risk.rule.vm.Consts.RULE_DIRECTORY;
import static com.rkylin.risk.rule.vm.Consts.TEMPLATE_DIRECTORY;

/**
 * Created by tomalloc on 16-3-4.
 */
@Slf4j
public class BuildRule {

  private Interpreter interpreter;

  private Configration configration;

  private boolean isConfig = false;
  private boolean isMake = false;
  private boolean isDeploy = false;

  private Path buildHome;

  public BuildRule(Configration configration) {
    this(configration, null);
  }

  public BuildRule(Configration configration, Interpreter interpreter) {
    Objects.requireNonNull(configration);
    this.configration = configration;
    this.buildHome = configration.getBuildHome();
    if (interpreter == null) {
      interpreter = InterpreterFactory.defaultInterpreter(buildHome);
    }
    this.interpreter = interpreter;
  }

  public void configure() {
    log.info("begin configure rule project at {}", buildHome);
    if (isConfig) {
      return;
    }
    configration.configure();
    isConfig = true;
  }

  private void generateKmoduleFile(ModuleInfo moduleInfo) {
    File kmoudleDir = new File(buildHome.toFile(), KMODULE_DIRECTORY);
    File kmoduleXML = new File(kmoudleDir, "kmodule.xml");
    if (StringUtils.isNotBlank(moduleInfo.getKmodulePath())) {
      configration.syncKmodule(kmoduleXML);
    } else {
      Map<String, String> map = new LinkedHashMap<>();
      map.put("groupId", moduleInfo.getArtifact().getGroupId());
      map.put("artifactId", moduleInfo.getArtifact().getArtifactId());
      interpreter.render(buildHome.resolve(BUILD_KMODULE_XML), kmoduleXML.toPath(),
          moduleInfo.getArtifact());
    }
  }

  public void make() {
    log.info("begin make rule project at {}", buildHome);
    if (isMake) {
      return;
    }
    if (!isConfig) {
      configure();
    }
    ModuleInfo moduleInfo = configration.getModuleInfo();
    Map<String, Object> data = moduleInfo.getRuleData();
    generateKmoduleFile(moduleInfo);
    log.info("start rendering maven {}/pom.xml", buildHome);
    interpreter.render(buildHome.resolve(BUILD_TEMPLATE_POM), buildHome.resolve(BUILD_POM),
        moduleInfo.getArtifact());
    interpreter.render(buildHome.resolve(BUILD_TEMPLATE_SETTINGS),
        buildHome.resolve(BUILD_SETTINGS), moduleInfo.getRepositoryInfo());

    interpreter.render(buildHome.resolve(TEMPLATE_DIRECTORY), buildHome.resolve(RULE_DIRECTORY),
        data);

    isMake = true;
  }

  /**
   * 部署规则文件到maven仓库
   */
  private void build(String command) {
    if (isDeploy) {
      return;
    }
    if (!isMake) {
      //如果没有构建执行构建
      make();
    }

    String settings = buildHome.resolve(BUILD_SETTINGS).toString();
    MavenCommand invokeMaven = new MavenCommand(buildHome.toString(), settings);
    invokeMaven.executeCommand(command);
    isDeploy = true;
  }

  public void deploy() {
    build("deploy");
  }

  public void makePackage() {
    build("package");
  }

  public void clean() {
    log.info("clean path {}", buildHome);
    try {
      if (Files.exists(buildHome)) {
        Files.walkFileTree(buildHome, new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
              throws IOException {
            Files.deleteIfExists(file);
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException exc)
              throws IOException {
            Files.deleteIfExists(dir);
            return FileVisitResult.CONTINUE;
          }
        });
      }
    } catch (IOException e) {
      log.info("delete directory error", e);
    }
  }
}
