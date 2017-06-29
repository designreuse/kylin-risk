package com.rkylin.risk.spring;

import com.rkylin.risk.spring.handler.SimpleUrlViewHandlerAdapter;
import com.rkylin.risk.spring.handler.SimpleUrlViewHandlerMapping;
import java.util.Properties;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Created by tomalloc on 16-7-21.
 */
public class URLViewParser extends AbstractBeanDefinitionParser {
  public static final String HANDLER_MAPPING_BEAN_NAME =
      SimpleUrlViewHandlerMapping.class.getName();
  public static final String HANDLER_ADAPTER_BEAN_NAME =
      SimpleUrlViewHandlerAdapter.class.getName();

  @Override
  protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
    Element mappingsElement = DomUtils.getChildElementByTagName(element, "mapping");
    String context = mappingsElement.getTextContent();
    PropertiesEditor editor = new PropertiesEditor();
    editor.setAsText(context);
    Properties properties = (Properties) editor.getValue();

    //
    Object source = parserContext.extractSource(element);

    CompositeComponentDefinition
        compDefinition = new CompositeComponentDefinition(element.getTagName(), source);
    parserContext.pushContainingComponent(compDefinition);

    XmlReaderContext readerContext = parserContext.getReaderContext();
    RootBeanDefinition handlerMappingDef =
        new RootBeanDefinition(SimpleUrlViewHandlerMapping.class);
    handlerMappingDef.setSource(source);
    handlerMappingDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    handlerMappingDef.getPropertyValues().add("order", 1);
    handlerMappingDef.getPropertyValues().add("urlViewMapping", properties);
    readerContext.getRegistry()
        .registerBeanDefinition(HANDLER_MAPPING_BEAN_NAME, handlerMappingDef);

    RootBeanDefinition handlerAdapterDef =
        new RootBeanDefinition(SimpleUrlViewHandlerAdapter.class);
    handlerAdapterDef.setSource(source);
    handlerAdapterDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    handlerAdapterDef.getPropertyValues().add("order", 1);
    readerContext.getRegistry()
        .registerBeanDefinition(HANDLER_ADAPTER_BEAN_NAME, handlerAdapterDef);

    parserContext.registerComponent(
        new BeanComponentDefinition(handlerMappingDef, HANDLER_MAPPING_BEAN_NAME));
    parserContext.registerComponent(
        new BeanComponentDefinition(handlerAdapterDef, HANDLER_ADAPTER_BEAN_NAME));

    parserContext.popAndRegisterContainingComponent();
    return null;
  }
}
