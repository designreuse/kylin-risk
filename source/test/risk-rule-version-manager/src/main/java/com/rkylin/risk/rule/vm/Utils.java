package com.rkylin.risk.rule.vm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Created by tomalloc on 16-4-29.
 */
public final class Utils {
  private Utils() {
  }

  private static final int TEMP_DIR_ATTEMPTS = 10000;

  public static URL getResource(String resourceName) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    if (loader == null) {
      loader = Utils.class.getClassLoader();
    }
    if (loader == null) {
      loader = ClassLoader.getSystemClassLoader();
    }
    Objects.requireNonNull(loader, "No classloader found");
    URL url = loader.getResource(resourceName);
    Objects.requireNonNull(url, resourceName + " doesn't exists");
    return url;
  }

  public static long copy(URL url, File file) {
    try (InputStream inputStream = url.openStream()) {
      return Files.copy(inputStream, file.toPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Path createTempDir() {
    Path baseDir = Paths.get(System.getProperty("java.io.tmpdir"));
    String baseName = System.currentTimeMillis() + "-";
    for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {
      Path tmpPath = baseDir.resolve(baseName + counter);
      try {
        return Files.createDirectory(tmpPath);
      } catch (IOException e) {
        //ignore exception
      }
    }
    throw new IllegalStateException("Failed to create directory within "
        + TEMP_DIR_ATTEMPTS + " attempts (tried "
        + baseName + "0 to " + baseName + (TEMP_DIR_ATTEMPTS - 1) + ')');
  }
}
