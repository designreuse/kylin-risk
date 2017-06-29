package com.rkylin.risk.service.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-11-1.
 */
@Setter
@Getter
@ToString(callSuper=true)
public class MitouUserInfoResponseParam extends MitouBaseResponseParam  {
  private int platuserid;
  private String mobile;
  private String availableAmount;
  private String mainStatus;
  private String cardIdStatus;
  private String contactStatus;
  private String mobileAuthStatus;
  private String audioStatus;
  private String creditAmount;
}
