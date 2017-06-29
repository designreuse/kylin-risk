package com.rkylin.risk.spring.handler.page;

import java.util.Map;

/**
 * 分页后台数据验证 Created by tomalloc on 16-7-27.
 */
public interface PageValidator {
  /**
   * 数据验证
   *
   * @param param 请求的参数
   * @param pageContext 上下文
   */
  void validate(Map<String, Object> param, PageContext pageContext);
}
