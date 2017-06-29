package com.rkylin.risk.rule.vm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-5-5.
 */
@Slf4j
public class UtilsTest {

  @Test
  public void createTempDirTest() {
    Path path = Utils.createTempDir();
    log.info("temp file:{}", path);
    assertThat(path).exists();
    try {
      Files.deleteIfExists(path);
    } catch (IOException e) {
    }
  }

  @Test
  public void getResourceTest() {
    Utils.getResource("com/rkylin/risk/rule/vm");
  }

  @Test
  public void copyTest() throws IOException {
    Class copyClass = Utils.class;
    String str = copyClass.getName().replace(".", "/") + ".class";
    URL url = Utils.getResource(str);
    Path path = Utils.createTempDir();
    File target = path.resolve(copyClass.getSimpleName() + ".class").toFile();
    Utils.copy(url, target);
    assertThat(target).exists();
    try (InputStream sourceStream = url.openStream();
         InputStream targetStream = Files.newInputStream(target.toPath())) {
      String sourceMd5 = DigestUtils.md5Hex(sourceStream);
      String targetMd5 = DigestUtils.md5Hex(targetStream);
      assertThat(sourceMd5).isEqualTo(targetMd5);
    }
  }
}
