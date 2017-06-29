package com.rkylin.risk.spring.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tomalloc on 16-7-20.
 */
public class SimpleUrlViewHandlerAdapter implements HandlerAdapter, Ordered {
  private int order;

  @Override public boolean supports(Object handler) {
    return handler instanceof ModelAndView;
  }

  @Override public ModelAndView handle(HttpServletRequest request, HttpServletResponse response,
      Object handler) throws Exception {
    return (ModelAndView) handler;
  }

  @Override public long getLastModified(HttpServletRequest request, Object handler) {
    return -1;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  @Override public int getOrder() {
    return order;
  }
}
