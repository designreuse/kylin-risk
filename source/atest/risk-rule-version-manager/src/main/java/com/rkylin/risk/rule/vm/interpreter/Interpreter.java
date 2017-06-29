package com.rkylin.risk.rule.vm.interpreter;

import com.rkylin.risk.rule.vm.model.MapBean;
import java.nio.file.Path;
import java.util.Map;

/**
 * 模板解释器 Created by tomalloc on 16-3-1.
 */
public interface Interpreter {


  void render(Path templatePath, Path savePath, Map<String, Object> model);
  /**
   * 将模板渲染生成文件
   *
   * @param templatePath 模板路径
   * @param savePath 渲染到的路径
   * @param bean 模板数据
   */
  void render(Path templatePath, Path savePath, MapBean bean);
}
