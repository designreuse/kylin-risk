package com.rkylin.risk.spring;

import java.util.List;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Created by tomalloc on 16-7-21.
 */
public class URLSqlParser extends AbstractBeanDefinitionParser {
  @Override
  protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
    String pageHandlerRef = element.getAttribute("pageHandlerRef");
    List<Element>
        sqlMapElements = DomUtils.getChildElementsByTagName(element, "sqlmap");
    if (sqlMapElements == null) {
      return null;
    }

    return null;
  }
}
