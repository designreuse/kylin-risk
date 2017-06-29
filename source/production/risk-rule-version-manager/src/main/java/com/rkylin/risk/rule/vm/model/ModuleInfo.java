package com.rkylin.risk.rule.vm.model;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomalloc on 16-5-9.
 */
@Setter
@Getter
public class ModuleInfo {

  private Artifact artifact;

  private RepositoryInfo repositoryInfo;

  /**
   * 规则文件路径
   */
  private String templateRulePath;

  /**
   * kmodule文件路径
   */
  private String kmodulePath;

  private Map ruleData;
}
