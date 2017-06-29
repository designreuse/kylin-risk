package com.rkylin.risk.credit;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by tomalloc on 16-7-27.
 */
public class RiskCreditNamespaceHandlerSupport extends NamespaceHandlerSupport {
  @Override public void init() {
    registerBeanDefinitionParser("credit", new CreditParser());
  }
}