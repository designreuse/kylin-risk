package com.rkylin.risk.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by 201508031790 on 2015/10/14.
 */
@Setter
@Getter
@ToString
public class ResultBean {

  /**
   * 规则ID
   */
  private String ruleids = "";

  /**
   * 总分数
   */
  private Double score = 0d;

  /**
   * 是否禁入:1-禁入；其他非禁入
   */
  private String isOff;

  /**
   * 原因
   */
  private String offMsg = "";
}
