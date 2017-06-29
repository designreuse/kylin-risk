package com.rkylin.risk.core.utils;

import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/16 version: 1.0
 */
public class BeanMappper {

  private static final ModelMapper MODELMAPPER = new ModelMapper();

  public static <T> T map(Object source, Class<T> destinationClass) {
    return MODELMAPPER.map(source, destinationClass);
  }

  public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
    List<T> destinationList = Lists.newArrayList();
    for (Object sourceObject : sourceList) {
      T destinationObject = MODELMAPPER.map(sourceObject, destinationClass);
      destinationList.add(destinationObject);
    }
    return destinationList;
  }

  public static void copy(Object source, Object destinationObject) {
    MODELMAPPER.map(source, destinationObject);
  }

  public static <T> T fastMap(Object source, Class<T> destinationClass) {
    try {
      T obj = destinationClass.newInstance();
      BeanUtils.copyProperties(source, obj);
      return obj;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void fastCopy(Object source, Object destinationObject) {
    BeanUtils.copyProperties(source, destinationObject);
  }

  public static <T> List<T> fastMapList(Collection sourceList, Class<T> destinationClass) {
    List<T> destinationList = Lists.newArrayList();
    for (Object sourceObject : sourceList) {
      T destinationObject = fastMap(sourceObject, destinationClass);
      destinationList.add(destinationObject);
    }
    return destinationList;
  }
}
