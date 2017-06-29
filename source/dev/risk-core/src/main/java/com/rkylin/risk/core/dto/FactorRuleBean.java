package com.rkylin.risk.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by lina on 2016-5-17.
 */
@Setter
@Getter
@ToString
public class FactorRuleBean {
  private String leftbrac;

  private String fields;

  private String conditions;

  private String conditionvals;

  private String rightbrac;

  private String logicsym;
}
