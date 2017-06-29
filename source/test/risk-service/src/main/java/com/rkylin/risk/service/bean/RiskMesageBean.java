package com.rkylin.risk.service.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qiuxian
 * @create 2016-09-30 12:56
 **/
@Setter
@Getter
public class RiskMesageBean {

  /*
风险提示
 */
  private String riskmsg;


  private  PersonFactor personFactor;
}
