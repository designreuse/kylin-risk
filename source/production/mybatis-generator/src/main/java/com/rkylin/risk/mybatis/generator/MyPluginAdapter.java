package com.rkylin.risk.mybatis.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/13 version: 1.0
 */
public class MyPluginAdapter extends PluginAdapter {
  private FullyQualifiedJavaType serializable;
  private FullyQualifiedJavaType gwtSerializable;
  private boolean addGWTInterface;
  private boolean suppressJavaInterface;

  public MyPluginAdapter() {
    super();
    serializable = new FullyQualifiedJavaType("Entity"); //$NON-NLS-1$
    gwtSerializable =
        new FullyQualifiedJavaType("com.google.gwt.user.client.rpc.IsSerializable"); //$NON-NLS-1$
  }

  @Override
  public void initialized(IntrospectedTable introspectedTable) {
    String mybatis3SqlMapNamespace = introspectedTable.getMyBatis3SqlMapNamespace();
    String exampleType = introspectedTable.getTableConfiguration().getDomainObjectName();

    introspectedTable.setMyBatis3FallbackSqlMapNamespace(exampleType);
  }

  @Override
  public boolean modelGetterMethodGenerated(Method method,
      TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
      IntrospectedTable introspectedTable,
      Plugin.ModelClassType modelClassType) {
    return false;
  }

  @Override
  public boolean modelSetterMethodGenerated(Method method,
      TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
      IntrospectedTable introspectedTable,
      Plugin.ModelClassType modelClassType) {
    return false;
  }

  @Override
  public boolean validate(List<String> warnings) {
    return true;
  }

  @Override
  public void setProperties(Properties properties) {
    super.setProperties(properties);
    addGWTInterface = Boolean.valueOf(properties.getProperty("addGWTInterface"));
    suppressJavaInterface =
        Boolean.valueOf(properties.getProperty("suppressJavaInterface"));
  }

  @Override
  public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable) {
    makeSerializable(topLevelClass, introspectedTable);
    return true;
  }

  @Override
  public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable) {
    makeSerializable(topLevelClass, introspectedTable);
    return true;
  }

  @Override
  public boolean modelRecordWithBLOBsClassGenerated(
      TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    makeSerializable(topLevelClass, introspectedTable);
    return true;
  }

  protected void makeSerializable(TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable) {
    if (addGWTInterface) {
      topLevelClass.addImportedType(gwtSerializable);
      topLevelClass.addSuperInterface(gwtSerializable);
    }

    if (!suppressJavaInterface) {
      topLevelClass.addImportedType(serializable);
      topLevelClass.addSuperInterface(serializable);

      Field field = new Field();
      field.setFinal(true);
      field.setInitializationString("1L");
      field.setName("serialVersionUID");
      field.setStatic(true);
      field.setType(new FullyQualifiedJavaType("long"));
      field.setVisibility(JavaVisibility.PRIVATE);
      context.getCommentGenerator().addFieldComment(field, introspectedTable);

      topLevelClass.addField(field);
    }
    topLevelClass.addAnnotation("@Getter");
    topLevelClass.addAnnotation("@Setter");
    topLevelClass.addAnnotation("@ToString");
    topLevelClass.addAnnotation("@EqualsAndHashCode");
    topLevelClass.addImportedType("lombok.Getter");
    topLevelClass.addImportedType("lombok.Setter");
    topLevelClass.addImportedType("lombok.ToString");
    topLevelClass.addImportedType("lombok.EqualsAndHashCode;");
  }

  @Override
  public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element,
      IntrospectedTable introspectedTable) {
    return false;
  }

  @Override
  public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
      IntrospectedTable introspectedTable) {
    return false;
  }

  @Override
  public boolean sqlMapInsertElementGenerated(XmlElement element,
      IntrospectedTable introspectedTable) {
    List<Attribute> attributeList = element.getAttributes();
    for (Attribute attribute : attributeList) {
      if ("id".equals(attribute.getName())) {
        setValue(attribute, "insert");
      } else if ("parameterType".equals(attribute.getName())) {
        String type = attribute.getValue();
        int lastFlag = type.lastIndexOf(".");
        String aliasTyp = type.substring(lastFlag + 1);
        setValue(attribute, aliasTyp);
      }
    }
    Element element1 = element.getElements().get(0);
    if (element1 instanceof XmlElement) {
      XmlElement xmlElement = (XmlElement) element1;
      if (xmlElement.getName().equals("selectKey")) {
        element.getElements().remove(0);
      }
    }
    List<IntrospectedColumn> columnList = introspectedTable.getPrimaryKeyColumns();
    List ls = new ArrayList() {
      {
        add("mediumint");
        add("smallint");
        add("int");
        add("tinyint");
      }
    };
    boolean isAutoPrimary = false;
    if (columnList != null) {
      for (IntrospectedColumn column : columnList) {
        if (column.getActualColumnName().toLowerCase().equals("id")
            && ls.indexOf(column.getJdbcTypeName()) > -1) {
          isAutoPrimary = true;
        }
      }
    }
    if (isAutoPrimary) {
      element.addAttribute(new Attribute("useGeneratedKeys", "true"));
      element.addAttribute(new Attribute("keyColumn", "id"));
      element.addAttribute(new Attribute("keyProperty", "id"));
    }
    return true;
  }

  private void setValue(Attribute attribute, String value) {
    try {
      java.lang.reflect.Field field = attribute.getClass().getDeclaredField("value");
      field.setAccessible(true);
      field.set(attribute, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String convertAlias(String s) {
    int lastFlag = s.lastIndexOf(".");
    String aliasTyp = s.substring(lastFlag + 1);
    return aliasTyp.toLowerCase();
  }

  @Override
  public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element,
      IntrospectedTable introspectedTable) {
    List<Attribute> attributeList = element.getAttributes();
    for (Attribute attribute : attributeList) {
      if ("id".equals(attribute.getName())) {
        setValue(attribute, "update");
      } else if ("parameterType".equals(attribute.getName())) {
        String type = attribute.getValue();
        int lastFlag = type.lastIndexOf(".");
        String aliasTyp = type.substring(lastFlag + 1);
        setValue(attribute, aliasTyp);
      }
    }
    return true;
  }

  @Override
  public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element,
      IntrospectedTable introspectedTable) {
    List<Attribute> attributeList = element.getAttributes();
    for (Attribute attribute : attributeList) {
      if ("id".equals(attribute.getName())) {
        setValue(attribute, "delete");
      } else if ("parameterType".equals(attribute.getName())) {
        String type = attribute.getValue();
        setValue(attribute, convertAlias(type));
      }
    }
    return true;
  }

  @Override
  public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element,
      IntrospectedTable introspectedTable) {
    List<Attribute> attributeList = element.getAttributes();
    for (Attribute attribute : attributeList) {
      if ("id".equals(attribute.getName())) {
        setValue(attribute, "get");
      } else if ("parameterType".equals(attribute.getName())) {
        String type = attribute.getValue();
        setValue(attribute, convertAlias(type));
      } else if ("resultMap".equals(attribute.getName())) {
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        String resultMap =
            domainObjectName.substring(0, 1).toLowerCase() + domainObjectName.substring(1) + "Map";
        System.out.println(domainObjectName + "\t" + resultMap);
        setValue(attribute, resultMap);
      }
    }
    List<Element> elementList = element.getElements();
    if (elementList != null) {
      for (Element element1 : elementList) {
        if (element1 instanceof XmlElement) {
          XmlElement xmlElement = (XmlElement) element1;
          if (xmlElement.getName().equals("include")) {
            List<Attribute> includeAttributeList = xmlElement.getAttributes();
            if (includeAttributeList != null) {
              for (Attribute includeAttribute : includeAttributeList) {
                if (includeAttribute.getName().equals("refid")) {
                  setValue(includeAttribute, "columnList");
                }
              }
            }
          }
          break;
        }
      }
    }
    return true;
  }

  @Override
  public boolean sqlMapBaseColumnListElementGenerated(XmlElement element,
      IntrospectedTable introspectedTable) {
    List<Attribute> attributeList = element.getAttributes();
    for (Attribute attribute : attributeList) {
      if ("id".equals(attribute.getName())) {
        setValue(attribute, "columnList");
      }
    }
    return true;
  }

  @Override
  public boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element,
      IntrospectedTable introspectedTable) {
    List<Attribute> attributeList = element.getAttributes();
    Attribute attributeId = null;
    String idName = null;
    for (Attribute attribute : attributeList) {
      if ("id".equals(attribute.getName())) {
        attributeId = attribute;
      } else if ("type".equals(attribute.getName())) {
        String type = attribute.getValue();
        int lastFlag = type.lastIndexOf(".");
        String aliasTyp = type.substring(lastFlag + 1);
        setValue(attribute, aliasTyp);
        idName = aliasTyp;
      }
    }
    if (idName != null & attributeId != null) {
      idName = idName.substring(0, 1).toLowerCase() + idName.substring(1) + "Map";
      setValue(attributeId, idName);
    }
    List<Element> elementList = element.getElements();
    for (Element attributeElement : elementList) {
      if (attributeElement instanceof XmlElement) {
        XmlElement xmlElement = (XmlElement) attributeElement;
        if ("result".equals(xmlElement.getName())) {
          List<Attribute> xmlAttributeList = xmlElement.getAttributes();
          boolean flag = false;
          Attribute attribute = null;
          for (Attribute xmlAttribute : xmlAttributeList) {
            if ("jdbcType".equals(xmlAttribute.getName())) {
              String value = xmlAttribute.getValue();
              if ("double".equals(value.toLowerCase())) {
                flag = true;
                attribute = new Attribute("javaType", "Amount");
              } else if ("decimal".equals(value.toLowerCase())) {
                flag = true;
                attribute = new Attribute("javaType", "Amount");
              } else if ("date".equals(value.toLowerCase())) {
                flag = true;
                attribute = new Attribute("javaType", "LocalDate");
              } else if ("timestamp".equals(value.toLowerCase())) {
                flag = true;
                attribute = new Attribute("javaType", "DateTime");
              }
              break;
            }
          }
          if (flag) {
            xmlAttributeList.add(attribute);
          }
        }
      }
    }

    return true;
  }
}
