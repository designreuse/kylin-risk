package com.rkylin.risk.credit.founder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-7-6.
 */
@Getter
@Setter
@ToString
public class CommonResult<T extends Data> {
  @JsonProperty("serialno")
  private String serialno;
  @JsonProperty("data")
  private T data;
}
