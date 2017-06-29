package com.rkylin.risk.boss.resteasy;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by tomalloc on 16-11-7.
 */
public class RestClientProxyFactory implements FactoryBean {
  private ResteasyWebTarget resteasyWebTarget;

  private Class proxyInterface;

  public RestClientProxyFactory(ResteasyWebTarget resteasyWebTarget,Class proxyInterface) {
    this.proxyInterface = proxyInterface;
    this.resteasyWebTarget=resteasyWebTarget;
  }

  @Override public Object getObject() throws Exception {
    return resteasyWebTarget.proxy(proxyInterface);
  }

  @Override public Class<?> getObjectType() {
    return proxyInterface;
  }

  @Override public boolean isSingleton() {
    return true;
  }
}
