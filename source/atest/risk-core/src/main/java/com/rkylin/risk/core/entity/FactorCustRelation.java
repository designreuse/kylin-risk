package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by 201508031790 on 2015/10/22.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class FactorCustRelation implements Entity {
  private Integer id;
  private Long custid;
  private Integer factorid;
  private String factorscore;
  private String factorcode;
  private static final long serialVersionUID = 1L;
}
