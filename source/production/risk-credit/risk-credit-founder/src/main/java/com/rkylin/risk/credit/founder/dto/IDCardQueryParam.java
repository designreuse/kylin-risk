package com.rkylin.risk.credit.founder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-7-7.
 */
@Setter
@Getter
@ToString
public class IDCardQueryParam {
  @JsonProperty("name")
  private String name;
  @JsonProperty("identityCard")
  private String idCard;
}
