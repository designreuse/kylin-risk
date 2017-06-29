package com.rkylin.risk.spring.handler.page;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证的上下文 可获取到当前请求的具体HttpServletRequest,同时维护对验证错误结果的存放 Created by tomalloc on 16-7-27.
 */
public class PageContext {
  /**
   * 当前的请求
   */
  private final HttpServletRequest request;
  /**
   * 当前请求的错误集合
   */
  private final Map<String, String> errorCollection = new LinkedHashMap<>();

  public PageContext(HttpServletRequest request) {
    this.request = request;
  }

  public HttpServletRequest request() {
    return request;
  }

  /**
   * 添加错误
   *
   * @param key 错误的key
   * @param value 错误的描述
   */
  public void addError(String key, String value) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(value);
    errorCollection.put(key, value);
  }

  public Map<String, String> errorCollection() {
    return errorCollection;
  }

  public boolean haseError() {
    return !errorCollection.isEmpty();
  }
}
