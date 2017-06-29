package com.rkylin.risk.rule.vm.interpreter;

import com.rkylin.risk.rule.vm.interpreter.builder.VelocityEngineBuilder;
import java.nio.file.Path;
import org.apache.commons.lang3.CharEncoding;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.CommonsLogLogChute;

/**
 * Created by tomalloc on 16-5-3.
 */
public final class InterpreterFactory {

  public static Interpreter defaultInterpreter(Path path) {
    VelocityEngine velocityEngine =
        new VelocityEngineBuilder(VelocityEngineBuilder.ResourceLoaderType.FILE)
            .loaderPath(path.toString())
            .runtimeLog(new CommonsLogLogChute())
            .openCache()
            .checkInterval(10)
            .inputEncoding(CharEncoding.UTF_8)
            .outputEncoding(CharEncoding.UTF_8)
            .build();
    return new VelocityInterpreter(velocityEngine, path);
  }
}
