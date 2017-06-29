package com.rkylin.risk.credit.founder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

/**
 * Created by tomalloc on 16-7-6.
 */
@Getter
@Setter
@ToString
public class BankCardFactorUltimateResult {
  /**
   * 验证结果
   */
  @JsonProperty("result")
  private VerifyResult verifyResult;

  /**
   * 描述
   */
  @JsonProperty("message")
  private String message;

  /**
   * 流水号
   */
  @JsonProperty("serialno")
  private String serialNo;

  @JsonProperty("accountno")
  private String accountNo;

  @JsonProperty("name")
  private String name;

  @JsonProperty("idcardnum")
  private String idCard;

  @JsonProperty("insertDate")
  private DateTime insertDate;

  @JsonProperty("appid")
  private String appid;

  @JsonProperty("bankpremobile")
  private String bankPreMobile;

}
