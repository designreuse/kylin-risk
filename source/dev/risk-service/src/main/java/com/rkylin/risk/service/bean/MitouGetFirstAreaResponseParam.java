package com.rkylin.risk.service.bean;

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
public class MitouGetFirstAreaResponseParam extends MitouBaseResponseParam {

  private String areaName;
  private String groupName;
  private String areaId;

}
