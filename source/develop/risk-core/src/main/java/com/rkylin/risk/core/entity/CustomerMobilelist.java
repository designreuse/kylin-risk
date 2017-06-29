package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class CustomerMobilelist implements Entity {

  private Long id;

  private String customerid;

  private String mobilelist;

  private static final long serialVersionUID = 1L;
}