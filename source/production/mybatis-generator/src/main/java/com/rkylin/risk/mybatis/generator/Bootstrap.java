package com.rkylin.risk.mybatis.generator;

import org.apache.ibatis.io.Resources;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/13 version: 1.0
 */
public class Bootstrap {

  public static void main(String[] args) throws Exception {
    List<String> warnings = new ArrayList<String>();
    boolean overwrite = true;
    File configFile = Resources.getResourceAsFile("generatorConfig.xml");
    ConfigurationParser cp = new ConfigurationParser(warnings);
    Configuration config = cp.parseConfiguration(configFile);
    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
    myBatisGenerator.generate(null);
  }
}
