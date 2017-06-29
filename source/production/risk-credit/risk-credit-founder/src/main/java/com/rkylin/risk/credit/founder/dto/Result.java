package com.rkylin.risk.credit.founder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-6-30.
 */
@Setter
@Getter
@ToString
public class Result<T extends Data> implements Serializable {
  @JsonProperty("Code")
  private String code;
  @JsonProperty("Msg")
  private String msg;

  /**
   * 只有当不扣费的时候才会有resulttype且等于1
   */
  @JsonProperty("resultType")
  private String resultType;
}
