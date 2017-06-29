package com.rkylin.risk.boss.intercept.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @company: rkylin
 * @author: tongzhu.yu
 * @since: 15-3-5 上午11:35 version: 1.0
 */
public class XssFilter implements Filter {

  private FilterConfig filterConfig;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    chain.doFilter(new XssHttpServletRequestWrapper(
        (HttpServletRequest) request), response);
  }

  @Override
  public void destroy() {
    this.filterConfig = null;
  }
}
