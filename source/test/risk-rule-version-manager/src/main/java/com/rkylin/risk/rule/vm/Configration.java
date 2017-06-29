package com.rkylin.risk.rule.vm;

import com.rkylin.risk.rule.vm.model.Artifact;
import com.rkylin.risk.rule.vm.model.ModuleInfo;
import com.rkylin.risk.rule.vm.model.RepositoryInfo;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.rkylin.risk.rule.vm.Consts.BUILD_TEMPLATE_POM;
import static com.rkylin.risk.rule.vm.Consts.BUILD_TEMPLATE_SETTINGS;
import static com.rkylin.risk.rule.vm.Consts.INIT_DIR;
import static com.rkylin.risk.rule.vm.Consts.MAVEN_POM;
import static com.rkylin.risk.rule.vm.Consts.MAVEN_SETTINGS;
import static com.rkylin.risk.rule.vm.Consts.TEMPLATE_DIRECTORY;
import static com.rkylin.risk.rule.vm.Consts.BUILD_KMODULE_XML;

/**
 * Created by tomalloc on 16-5-4.
 */
@Slf4j public class Configration {
  @Getter
  private Path buildHome;

  @Getter
  private ModuleInfo moduleInfo;

  public Configration(ModuleInfo moduleInfo) {
    this(Utils.createTempDir(), moduleInfo);
  }

  public Configration(Path buildHome, ModuleInfo moduleInfo) {
    if (buildHome == null || Files.notExists(buildHome)) {
      throw new IllegalStateException("buildHomeDirectory isn't exists");
    }
    this.buildHome = buildHome;
    validate(moduleInfo);
    this.moduleInfo = moduleInfo;
  }

  /**
   * 校验meta
   *
   * @param moduleInfo 构建规则的maven元数据
   */
  private void validate(ModuleInfo moduleInfo) {
    Objects.requireNonNull(moduleInfo, "the metadata is not null");
    Artifact artifact = moduleInfo.getArtifact();
    Objects.requireNonNull(artifact);

    log.info("groupId={},artifactId={},version={} will build at {}",
        artifact.getGroupId(), artifact.getArtifactId(),
        artifact.getVersion(), buildHome.toString());

    if (StringUtils.isBlank(artifact.getGroupId())) {
      throw new RuntimeException("the metadata groupid is not null");
    }
    if (StringUtils.isBlank(artifact.getArtifactId())) {
      throw new RuntimeException("the metadata artifactId is not null");
    }
    if (StringUtils.isBlank(artifact.getVersion())) {
      throw new RuntimeException("the metadata version is not null");
    }

    RepositoryInfo repositoryInfo = moduleInfo.getRepositoryInfo();
    Objects.requireNonNull(repositoryInfo);
    Objects.requireNonNull(repositoryInfo.getUserName());
    Objects.requireNonNull(repositoryInfo.getPassWord());
    Objects.requireNonNull(moduleInfo.getRuleData());

    if (StringUtils.isBlank(moduleInfo.getTemplateRulePath())) {
      throw new RuntimeException("the template rule path is not null");
    }
  }

  /**
   * 同步文件
   */
  private void sync(String path, File targetFile) {
    URL sourceURL = Utils.getResource(path);
    Utils.copy(sourceURL, targetFile);
  }

  /**
   * 同步kmodule.xml
   */
  public void syncKmodule(File kmoduleXML) {
    String kmoduleFilePath = moduleInfo.getKmodulePath();
    sync(kmoduleFilePath, kmoduleXML);
  }

  /**
   * 同步pom.xml模板文件
   */
  private void syncMavenPomTemplate() {
    sync(MAVEN_POM, new File(buildHome.toFile(), BUILD_TEMPLATE_POM));
  }

  private void syncDefaultKmoduleFile() {
    sync(Consts.DEFAULT_KMODULE_FILE, new File(buildHome.toFile(), BUILD_KMODULE_XML));
  }

  private boolean syncFile(final Path source, final Path target) throws IOException {
    final AtomicBoolean isExistsFile = new AtomicBoolean(false);
    Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
      Path currentPath = target;

      @Override public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
          throws IOException {
        if (dir != null && !dir.equals(source)) {
          currentPath = currentPath.resolve(dir.getFileName());
          Files.createDirectory(currentPath);
        }
        return FileVisitResult.CONTINUE;
      }

      @Override public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
          throws IOException {
        if (file != null) {
          isExistsFile.set(true);
          Files.copy(file, currentPath.resolve(file.getFileName()));
        }
        return FileVisitResult.CONTINUE;
      }

      @Override public FileVisitResult postVisitDirectory(Path dir, IOException exc)
          throws IOException {
        if (dir != null) {
          currentPath = currentPath.getParent();
        }
        return FileVisitResult.CONTINUE;
      }
    });
    return isExistsFile.get();
  }

  private boolean syncZipSystem(String resourcePath, final Path target) {
    String jarFlag = ".jar!";
    int jarIndex = resourcePath.lastIndexOf(jarFlag) + jarFlag.length() - 1;
    String jarPath = resourcePath.substring(0, jarIndex);
    String resourceDir = resourcePath.substring(jarIndex + 1);
    URI uri = URI.create(jarPath);
    Map<String, String> env = new HashMap<>();

    try (FileSystem fileSystem = FileSystems.newFileSystem(uri, env)) {
      final Path path = fileSystem.getPath(resourceDir);
      return syncFile(path, target);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 同步规则模板
   */
  private void syncRuleTemplate(Path templateDir) {
    String ruleSourcePath = moduleInfo.getTemplateRulePath();
    try {
      URL rulePathURL = Utils.getResource(ruleSourcePath);
      String protocol = rulePathURL.getProtocol();
      boolean isExistsFile = false;
      switch (protocol.toLowerCase()) {
        case "file":
          Path path = new File(
              URLDecoder.decode(rulePathURL.getPath(), StandardCharsets.UTF_8.name())).toPath();
          isExistsFile = syncFile(path, templateDir);
          break;
        case "jar":
          String sourcePath =
              URLDecoder.decode(rulePathURL.toExternalForm(), StandardCharsets.UTF_8.name());
          isExistsFile = syncZipSystem(sourcePath, templateDir);
          break;
        default:
          throw new IllegalStateException("The template rule must be the war child directory ");
      }
      if (!isExistsFile) {
        throw new NoSuchFileException("No exits any template rule file");
      }
    } catch (final Exception e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  private void syncMavenSettingsTemplate() {
    sync(MAVEN_SETTINGS, new File(buildHome.toFile(), BUILD_TEMPLATE_SETTINGS));
  }

  private void generateArchetype() {
    for (String path : INIT_DIR) {
      Path dir = buildHome.resolve(path);
      try {
        if (Files.notExists(dir)) {
          Files.createDirectories(dir);
        }
      } catch (IOException e) {
        throw new RuntimeException("create " + dir.toAbsolutePath() + " fail");
      }
    }
  }

  protected void configure() {
    generateArchetype();

    syncMavenSettingsTemplate();
    syncMavenPomTemplate();

    syncDefaultKmoduleFile();

    Path templateDir = buildHome.resolve(TEMPLATE_DIRECTORY);
    try {
      Files.createDirectories(templateDir);
      syncRuleTemplate(templateDir);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
