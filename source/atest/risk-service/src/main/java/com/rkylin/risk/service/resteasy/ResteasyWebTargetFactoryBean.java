package com.rkylin.risk.service.resteasy;

import java.util.Iterator;
import java.util.Set;
import lombok.Setter;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by tomalloc on 16-11-7.
 */
public class ResteasyWebTargetFactoryBean
    implements FactoryBean<ResteasyWebTarget>, InitializingBean {
  private String domain;

  @Setter
  private Set<Class> componentClass;
  @Setter
  private Set componentObject;

  private ResteasyWebTarget webTarget;

  public ResteasyWebTargetFactoryBean(String domain) {
    this.domain = domain;
  }

  @Override public ResteasyWebTarget getObject() throws Exception {
    if (webTarget == null) {
      afterPropertiesSet();
    }
    return webTarget;
  }

  @Override public Class<?> getObjectType() {
    return null;
  }

  @Override public boolean isSingleton() {
    return true;
  }

  @Override public void afterPropertiesSet() throws Exception {
    if (webTarget != null) {
      return;
    }

    ResteasyClient client = new ResteasyClientBuilder().build();
    if (componentClass != null) {
      for (Iterator<Class> iterator = componentClass.iterator(); iterator.hasNext(); ) {
        Class component = iterator.next();
        client.register(component);
      }
    }
    if (componentObject != null) {
      for (Iterator iterator = componentObject.iterator(); iterator.hasNext(); ) {
        client.register(iterator.next());
      }
    }
    this.webTarget = client.target(domain);
  }
}
