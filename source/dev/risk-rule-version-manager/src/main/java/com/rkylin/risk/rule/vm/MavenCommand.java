package com.rkylin.risk.rule.vm;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.cli.MavenCli;

/**
 * Created by tomalloc on 16-3-1.
 */
@Slf4j
public class MavenCommand {

  private String projectDirectory;
  private String settingsPath;

  public MavenCommand(String projectDirectory, String settingsPath) {
    this.projectDirectory = projectDirectory;
    this.settingsPath = settingsPath;
  }

  public void deploy() {
    executeCommand("deploy");
  }

  /**
   * maven 模块所在的目录
   */
  public void executeCommand(String command) {
    log.info("Building and deploying Maven project from basedir '{}'.", projectDirectory);
    ClassLoader classLoaderBak = Thread.currentThread().getContextClassLoader();
    MavenCli cli = new MavenCli();

    //设置maven project所在的目录
    System.setProperty(MavenCli.MULTIMODULE_PROJECT_DIRECTORY, projectDirectory);

    //设置maven参数
    String[] args = new ConfigurationMavenArgs().batchMode().quietOutput().showErrors()
        .settings(settingsPath).command(command).config();

    int mvnRunResult = cli.doMain(args, projectDirectory, System.out, System.out);
    if (mvnRunResult != 0) {
      throw new RuntimeException(
          "Error while building Maven project from basedir " + projectDirectory
              + ". Return code=" + mvnRunResult);
    }
    Thread.currentThread().setContextClassLoader(classLoaderBak);
    log.debug("Maven project successfully built and deployed!");
  }

  /**
   * maven命令参数生成器
   */
  private static final class ConfigurationMavenArgs {
    private final List<String> mvnArgs;

    private ConfigurationMavenArgs() {
      mvnArgs = new ArrayList<>();
    }

    public ConfigurationMavenArgs batchMode() {
      mvnArgs.add("-B");
      return this;
    }

    public ConfigurationMavenArgs showErrors() {
      mvnArgs.add("-e");
      return this;
    }

    public ConfigurationMavenArgs settings(final String settingsPath) {
      if (StringUtils.isNotBlank(settingsPath)) {
        mvnArgs.add("-s");
        mvnArgs.add(settingsPath);
      }
      return this;
    }

    public ConfigurationMavenArgs quietOutput() {
      mvnArgs.add("-q");
      return this;
    }

    public ConfigurationMavenArgs command(final String command) {
      if (StringUtils.isNotBlank(command)) {
        mvnArgs.add(command);
      }
      return this;
    }

    public String[] config() {
      return mvnArgs.toArray(new String[mvnArgs.size()]);
    }
  }
}
