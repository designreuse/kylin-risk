package com.rkylin.risk.rule.vm.interpreter;

import com.rkylin.risk.rule.vm.model.MapBean;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Objects;

/**
 * Created by tomalloc on 16-5-3.
 */
public abstract class AbstractInterpreter implements Interpreter {
  @Override public void render(Path templatePath, Path savePath, MapBean bean) {
    render(templatePath, savePath, bean.toMap());
  }

  /**
   * 渲染文件
   */
  abstract Path renderFile(Path templatePath, Path savePath, Map<String, Object> model);

  public void render(final Path templatePath, Path savePath, final Map<String, Object> model) {
    Objects.requireNonNull(templatePath);
    Objects.requireNonNull(savePath);
    Objects.requireNonNull(model);
    if (Files.notExists(templatePath)) {
      throw new IllegalArgumentException(templatePath.toAbsolutePath() + " not exists");
    }
    if (Files.exists(savePath) && !Files.isDirectory(savePath)) {
      throw new IllegalArgumentException(savePath.toAbsolutePath() + " already exists");
    }
    if (!Files.isDirectory(templatePath)) {
      if (Files.exists(savePath) && Files.isDirectory(savePath)) {
        savePath = savePath.resolve(templatePath.getFileName());
      }
      if (Files.notExists(savePath.getParent())) {
        try {
          Files.createDirectories(savePath.getParent());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      renderFile(templatePath, savePath, model);
      return;
    }

    if (Files.notExists(savePath)) {
      try {
        Files.createDirectories(savePath);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    final Path parentRenderRootDir = savePath;
    try {
      Files.walkFileTree(templatePath, new SimpleFileVisitor<Path>() {
        Path parentRenderDir = parentRenderRootDir;

        @Override public FileVisitResult postVisitDirectory(Path dir, IOException exc)
            throws IOException {
          Objects.requireNonNull(dir);
          parentRenderDir = parentRenderDir.getParent();
          return FileVisitResult.CONTINUE;
        }

        @Override public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
            throws IOException {
          Objects.requireNonNull(dir);
          if (!dir.equals(templatePath)) {
            parentRenderDir = parentRenderDir.resolve(dir.getFileName());
            Files.createDirectory(parentRenderDir);
          }
          return FileVisitResult.CONTINUE;
        }

        @Override public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException {
          Objects.requireNonNull(file);
          renderFile(file, parentRenderDir.resolve(file.getFileName()), model);
          return FileVisitResult.CONTINUE;
        }

        @Override public FileVisitResult visitFileFailed(Path file, IOException exc)
            throws IOException {
          return super.visitFileFailed(file, exc);
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
