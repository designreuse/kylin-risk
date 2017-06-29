package com.rkylin.risk.boss.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-1-27 下午3:02 version: 1.0
 */
@Slf4j
public class ApplicationFactory {
  private static WebApplicationContext webApplicationContext;

  static {
    try {
      webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
    } catch (Throwable e) {
      log.error("加载spring application错误", e);
    }
  }

  public static final <T> T getBean(String beanName) {
    if (webApplicationContext != null) {
      return (T) webApplicationContext.getBean(beanName);
    }
    return null;
  }
}
