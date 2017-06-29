package com.rkylin.risk.rule.vm.interpreter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import static java.nio.file.StandardOpenOption.CREATE;

/**
 * 模板解释器 Created by tomalloc on 16-3-1.
 */
public class VelocityInterpreter extends AbstractInterpreter {
  private VelocityEngine velocityEngine;
  private Path buildHome;
  private final String extensionName = "vm";

  public VelocityInterpreter(VelocityEngine velocityEngine, Path buildHome) {
    this.velocityEngine = velocityEngine;
    this.buildHome = buildHome;
  }

  Path renderFile(Path templatePath, Path savePath, Map<String, Object> model) {
    Writer writer = null;
    try {
      String saveFilePath = savePath.toAbsolutePath().toString();
      if (FilenameUtils.isExtension(saveFilePath, extensionName)) {
        String realFileName = FilenameUtils.getBaseName(saveFilePath);
        savePath = savePath.getParent().resolve(realFileName);
      }
      OutputStream outputStream = Files.newOutputStream(savePath, CREATE);
      writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
      VelocityContext velocityContext = new VelocityContext(model);
      velocityEngine.mergeTemplate(buildHome.relativize(templatePath).toString(),
          StandardCharsets.UTF_8.name(), velocityContext,
          writer);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return savePath;
  }
}
