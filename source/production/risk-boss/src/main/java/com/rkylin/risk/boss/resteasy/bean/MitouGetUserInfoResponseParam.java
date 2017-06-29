package com.rkylin.risk.boss.resteasy.bean;

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

  private String contractStatus;
  private String contractReason;
  private SceneStatus sceneStatus;
  private String bankInfoStatus;
  private String bankInfoReason;
  private String workInfoStatus;
  private String workInfoReason;
  private String onlineRetailerReason;
  private String onlineRetailerStatus;
  private String matesCardidReason;
  private String matesCardidStatus;
  private String orderReason;
  private String orderStatus;

  @Getter
  @Setter
  public class SceneStatus {
    private String innNet;
    private String medicalBeauty;
    private String tripShoot;
    private String tourism;

    @Override public String toString() {
      return "{"
          + "medicalBeauty="
          + medicalBeauty
          + ", innNet="
          + innNet
          + ", tripShoot="
          + tripShoot
          + ", tourism="
          + tourism
          + "}";
    }
  }
}
