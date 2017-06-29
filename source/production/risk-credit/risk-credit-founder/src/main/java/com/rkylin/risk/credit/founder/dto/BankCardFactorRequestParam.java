package com.rkylin.risk.credit.founder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-6-30.
 */
@Setter
@Getter
@ToString
public class BankCardFactorRequestParam {
  /**
   * 用户名
   */
  @JsonProperty("NAME")
  private String name;
  /**
   * 帐户号
   */
  @JsonProperty("ACCOUNTNO")
  private String accountNo;

  /**
   * 身份证号
   */
  @JsonProperty("IDCARDNUM")
  private String idCard;

  /**
   * 银行预留手机号
   */
  @JsonProperty("BANKPREMOBILE")
  private String bankMobile;
}
