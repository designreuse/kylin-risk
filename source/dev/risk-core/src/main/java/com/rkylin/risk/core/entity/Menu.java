package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Menu implements Entity, Comparator {
  private Short id;

  private String menuname;

  private String menulevel;

  private String menuurl;

  private Short displayorder;

  private Short parentid;

  private String status;

  private static final long serialVersionUID = 1L;

  private List<Menu> children;

  @Override
  public int compare(Object o1, Object o2) {

    Menu m1 = (Menu) o1;
    Menu m2 = (Menu) o2;
    if (m1.getDisplayorder() > m2.getDisplayorder()) {
      return 1;
    } else {
      if (m1.getDisplayorder().equals(m2.getDisplayorder())) {
        return 0;
      } else {
        return -1;
      }
    }
  }
}