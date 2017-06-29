package com.rkylin.risk.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by tomalloc on 16-7-21.
 */
public class RiskSpringSupportNamespaceHandler extends NamespaceHandlerSupport {
  @Override public void init() {
    registerBeanDefinitionParser("url-view", new URLViewParser());
    registerBeanDefinitionParser("url-sql", new URLSqlParser());
  }
}
