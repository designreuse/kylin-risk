package com.rkylin.risk.boss.resteasy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.boss.resteasy.component.EncryptFormDataConverterProvider;
import com.rkylin.risk.boss.resteasy.component.Jackson2Provider;
import com.rkylin.risk.boss.resteasy.component.URLRequestDecodeFilter;
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
  private String publicKey;

  @Setter
  private ObjectMapper objectMapper;
  private ResteasyWebTarget webTarget;

  public ResteasyWebTargetFactoryBean(String domain, String publicKey) {
    this.domain = domain;
    this.publicKey = publicKey;
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
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
    }
    EncryptFormDataConverterProvider encryptFormDataConverterProvider =
        new EncryptFormDataConverterProvider(publicKey, objectMapper);
    ResteasyClient client = new ResteasyClientBuilder().build();

    // 加载组件
    client.register(Jackson2Provider.class);
    client.register(encryptFormDataConverterProvider);
    client.register(URLRequestDecodeFilter.class);

    this.webTarget = client.target(domain);
  }
}
