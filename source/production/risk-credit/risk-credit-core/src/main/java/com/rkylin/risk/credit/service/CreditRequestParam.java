package com.rkylin.risk.credit.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-7-28.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"name", "idNumber", "mobile", "bankCard"})
public class CreditRequestParam {
  /**
   * 用户名
   */
  private String name;
  /**
   * 身份证号
   */
  private String idNumber;
  /**
   * 手机号
   */
  private String mobile;
  /**
   * 银行卡号
   */
  private String bankCard;
}
