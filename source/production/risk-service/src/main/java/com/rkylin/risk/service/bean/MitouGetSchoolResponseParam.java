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
public class MitouGetSchoolResponseParam extends MitouBaseResponseParam {

  private String id;
  private String universityName;
  private String name;

}
