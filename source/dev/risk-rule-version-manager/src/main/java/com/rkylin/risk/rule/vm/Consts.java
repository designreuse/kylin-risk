package com.rkylin.risk.rule.vm;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomalloc on 16-5-4.
 */
public final class Consts {
  public static final String RULE_DIRECTORY = "src/main/resources";
  public static final String KMODULE_DIRECTORY = "src/main/resources/META-INF";
  public static final String TEMPLATE_DIRECTORY = "tmp/template";
  public static final String TMP_BUILD = "tmp/maven";

  public static final Set<String> INIT_DIR = new HashSet<String>();

  static {
    INIT_DIR.add(RULE_DIRECTORY);
    INIT_DIR.add(KMODULE_DIRECTORY);
    INIT_DIR.add(TEMPLATE_DIRECTORY);
    INIT_DIR.add(TMP_BUILD);
  }

  public static final String MAVEN_POM = "maven/pom.xml.vm";
  public static final String MAVEN_SETTINGS = "maven/settings.xml.vm";

  public static final String BUILD_TEMPLATE_POM = "tmp/maven/pom.xml.vm";
  public static final String BUILD_TEMPLATE_SETTINGS = "tmp/maven/settings.xml.vm";
  public static final String BUILD_KMODULE_XML = "tmp/kmodule.xml";

  public static final String BUILD_POM = "pom.xml";
  public static final String BUILD_SETTINGS = ".settings.xml";

  public static final String DEFAULT_KMODULE_FILE = "default/kmodule.xml.vm";
}
