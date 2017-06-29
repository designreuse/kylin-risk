package com.rkylin.risk.mybatis.cache.client.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/5 version: 1.0
 */
public class SpringContextUtils implements ApplicationContextAware, BeanFactoryPostProcessor {

  private static ApplicationContext context;

  public static ApplicationContext getContext() {
    return context;
  }

  public static <T> T getBean(String name) {
    return (T) context.getBean(name);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {

  }
}
