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
public class BankCardFactorResult implements Data {

  @JsonProperty("result")
  private BankCardFactorUltimateResult result;
}
