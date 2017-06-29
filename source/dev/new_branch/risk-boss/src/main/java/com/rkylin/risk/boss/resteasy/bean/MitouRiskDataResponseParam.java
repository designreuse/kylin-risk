package com.rkylin.risk.boss.resteasy.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author qiuxian
 * @create 2016-11-04 11:35
 **/
@Setter
@Getter
@ToString(callSuper = true)
public class MitouRiskDataResponseParam extends MitouBaseResponseParam {


  /**
   *之前提交过的并审核通过的数据key组成的数组
   **/
  @JsonProperty("re_error")
  private String[] reerror;

  /**
   *之前提交过的数据的key组成的数组
   **/
  @JsonProperty("re_pass")
  private String[] repass;



}
