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
public class MitouGetUserInfoResponseParam extends MitouBaseResponseParam {

  private String platuserid;
  private String mobile;
  private Double availableAmount;
  private String mainStatus;
  private String cardIdStatus;
  private String contactStatus;
  private String mobileAuthStatus;
  private String audioStatus;
  private Double creditAmount;
  private String creidtScore;
  private String studentInfoStatus;
  private String msg;
  private String cardIdReason;
  private String studentReason;
  private String contactReason;
  private String mobileAuthReason;
}
