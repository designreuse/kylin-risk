package com.rkylin.risk.kie.spring.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by tomalloc on 16-8-10.
 */
public class RiskKieSpringNamespaceHandler extends NamespaceHandlerSupport {
  @Override public void init() {
    registerBeanDefinitionParser("risk-kie-server", new KieContainerSessionDefinitionParser());
  }
}
