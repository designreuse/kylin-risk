package com.rkylin.risk.rule.vm.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by tomalloc on 16-5-9.
 */
@Setter
@Getter
public class Artifact implements MapBean {
  /**
   * The groupId that this directory represents, if any.
   */
  private String groupId;

  /**
   * The artifactId that this directory represents, if any.
   */
  private String artifactId;

  /**
   * The version that this directory represents, if any. It is used for artifact snapshots only.
   */
  private String version;

  /**
   * 模块名称
   */
  private String moduleName;

  /**
   * jar部署的路径
   */
  private String distributionUrl;

  @Override
  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("groupId", groupId);
    map.put("artifactId", artifactId);
    map.put("version", version);
    String tmpModuleName = moduleName;
    if (StringUtils.isBlank(tmpModuleName)) {
      String projectName = groupId;
      int lastDotIndex = projectName.lastIndexOf(".");
      if (lastDotIndex != -1 && lastDotIndex != projectName.length() - 1) {
        projectName = projectName.substring(lastDotIndex + 1);
      }
      tmpModuleName = projectName + "::" + artifactId;
    }
    map.put("moduleName", tmpModuleName);
    map.put("distributionUrl", distributionUrl);
    return map;
  }
}
