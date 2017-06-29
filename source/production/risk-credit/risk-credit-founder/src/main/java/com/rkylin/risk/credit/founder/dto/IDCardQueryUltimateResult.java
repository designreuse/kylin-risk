package com.rkylin.risk.credit.founder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

/**
 * Created by tomalloc on 16-7-7.
 */
@Setter
@Getter
@ToString
public class IDCardQueryUltimateResult implements Data {
  @JsonProperty("name")
  private String name;

  @JsonProperty("compresult")
  private String compresult;

  @JsonProperty("identitycard")
  private String idCard;

  @JsonProperty("photo")
  private String photo;

  @JsonProperty("serialno")
  private String serialno;

  @JsonProperty("appid")
  private String appid;

  @JsonProperty("insertDate")
  private DateTime insertDate;
}
