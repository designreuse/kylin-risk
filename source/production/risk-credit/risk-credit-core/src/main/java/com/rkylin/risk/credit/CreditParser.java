package com.rkylin.risk.credit;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * credit标签的解析
 * Created by tomalloc on 16-7-27.
 */
public class CreditParser extends AbstractBeanDefinitionParser {
  @Override
  protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
    return null;
  }
}
