package com.rkylin.risk.boss.resteasy.bean;

import com.rkylin.risk.boss.resteasy.EncryptParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qiuxian
 * @create 2016-11-04 14:26
 **/
@Setter
@Getter
public class MitouRiskDataRequestParam implements EncryptParam {
  private String platuserid;
  private MitouRiskDataBean risk_data;

}
