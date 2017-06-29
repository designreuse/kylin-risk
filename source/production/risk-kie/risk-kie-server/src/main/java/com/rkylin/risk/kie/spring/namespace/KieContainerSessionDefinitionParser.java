package com.rkylin.risk.kie.spring.namespace;

import com.rkylin.risk.kie.spring.factorybeans.KieContainerFactoryBean;
import com.rkylin.risk.kie.spring.factorybeans.ScannerReleaseId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.kie.api.builder.ReleaseId;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * 自定义标签risk-kie-server解析 Created by tomalloc on 16-8-10.
 */
public class KieContainerSessionDefinitionParser extends AbstractBeanDefinitionParser {
  private static final String RELEASEID_SNIFFER = "releaseIdSniffer";

  /**
   * 延迟上限5分钟
   */
  private static final long DELAY_LIMIT = 5 * 60 * 1000;

  @Override
  protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
    Object source = parserContext.extractSource(element);
    CompositeComponentDefinition
        compDefinition = new CompositeComponentDefinition(element.getTagName(), source);
    parserContext.pushContainingComponent(compDefinition);

    String id = element.getAttribute("id");
    String setting = element.getAttribute("setting");

    List<Element>
        releaseIdElements = DomUtils.getChildElementsByTagName(element, "releaseId");
    Set<ScannerReleaseId> scannerReleaseIdSet =
        new HashSet<>((int) (releaseIdElements.size() / .75f) + 1);
    int i = 1;
    for (Element releaseIdElement : releaseIdElements) {
      String groupId = releaseIdElement.getAttribute("groupId");
      String artifactId = releaseIdElement.getAttribute("artifactId");
      String version = releaseIdElement.getAttribute("version");
      boolean scannerEnabled =
          Boolean.parseBoolean(releaseIdElement.getAttribute("scannerEnabled"));
      long scannerInterval = Long.parseLong(releaseIdElement.getAttribute("scannerInterval"));
      long delay;
      //设置延迟时间
      if (releaseIdElement.hasAttribute("delay")) {
        delay = Long.parseLong(releaseIdElement.getAttribute("delay"));
      } else {
        int tmpDelay = i * 1000;
        delay = tmpDelay;
        if (tmpDelay > DELAY_LIMIT) {
          delay = DELAY_LIMIT;
        }
      }
      String scannerId = releaseIdElement.getAttribute("scannerId");
      ReleaseId releaseId = new ReleaseIdImpl(groupId, artifactId, version);
      ScannerReleaseId scannerReleaseId = new ScannerReleaseId();
      scannerReleaseId.setReleaseId(releaseId);
      scannerReleaseId.setScannerEnabled(scannerEnabled);
      scannerReleaseId.setScannerInterval(scannerInterval);
      scannerReleaseId.setDelay(delay);
      if (scannerId != null && !"".equals(scannerId.trim())) {
        scannerReleaseId.setScannerId(scannerId);
      }
      scannerReleaseIdSet.add(scannerReleaseId);
      i++;
    }
    RootBeanDefinition compositeBeanDef = new RootBeanDefinition(KieContainerFactoryBean.class);
    compositeBeanDef.setSource(source);
    compositeBeanDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    if (setting != null && !setting.trim().equals("")) {
      compositeBeanDef.getPropertyValues().add("setting", setting);
    }
    compositeBeanDef.getPropertyValues().add("scannerReleaseIdSet", scannerReleaseIdSet);
    compositeBeanDef.getPropertyValues()
        .add("releaseIdSniffer", element.getAttribute("releaseIdSniffer"));
    parserContext.getReaderContext().getRegistry().registerBeanDefinition(id, compositeBeanDef);
    parserContext.registerComponent(new BeanComponentDefinition(compositeBeanDef, id));
    parserContext.popAndRegisterContainingComponent();
    return null;
  }
}
