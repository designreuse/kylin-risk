package com.rkylin.risk.service.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rkylin.risk.service.resteasy.EncryptParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author qiuxian
 * @create 2016-11-04 14:26
 **/
@Setter
@Getter
@ToString
public class MitouRiskDataRequestParam implements EncryptParam {
  private String platuserid;
  @JsonProperty("risk_data")
  private MitouRiskDataBean riskData;
  @JsonProperty("img_delivery_mode")
  private Integer  imgDeliveryMode;
  @JsonProperty("img_decode_key")
  private String  imgDecodeKey;
  private Integer dataSource;
  @JsonProperty("first_key_tags ")
  private Integer firstKeyTags;

}
