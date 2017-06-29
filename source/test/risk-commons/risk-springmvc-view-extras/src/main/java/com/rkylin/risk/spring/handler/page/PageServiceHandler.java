package com.rkylin.risk.spring.handler.page;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页处理器
 * Created by tomalloc on 16-10-19.
 */
public interface PageServiceHandler<T> {
  T query(HttpServletRequest request);
}
