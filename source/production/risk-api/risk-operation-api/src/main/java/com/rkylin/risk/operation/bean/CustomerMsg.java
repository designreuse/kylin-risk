package com.rkylin.risk.operation.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by lina on 2016-3-14.
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = { "customername", "certificateid" })
public class CustomerMsg implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 客户编号
   */
  private String customerid;
  /**
   * 客户名称
   */
  private String customername;
  /**
   *证件类型
   */
  private String certificatetype;
  /**
   * 证件号码
   */
  private String certificateid;
  /**
   * 年龄1
   */
  private String age;
  /**
   * 婚姻1
   */
  private String marriage;
  /**
   * 户籍1
   */
  private String registArea;
  /**
   * 本地居住时间1
   */
  private String resideTime;
  /**
   * 文化程度1
   */
  private String eduDegree;
  /**
   * 工作单位1
   */
  private String comType;
  /**
   * 工作职位1
   */
  private String position;
  /**
   *入职时间1
   */
  private String entryTime;
  /**
   *月收入标准1
   */
  private String wage;
  /**
   *房产类型1
   */
  private String houseType;
  /**
   *房产面积1
   */
  private String houseSize;
  /**
   *房产地域1
   */
  private String houseRegion;
  /**
   *房产区域1
   */
  private String houseArea;
  /**
   *抵押情况1
   */
  private String mortInfo;
  /**
   *其他净资产折现1
   */
  private String otherEstate;
  /**
   *租金比1
   */
  private String rentRate;
  /**
   *负债比1
   */
  private String burdenRate;
  /**
   *保证金比例1
   */
  private String cashDepositRate;
  /**
   *融资年限1
   */
  private String financingTime;
  /**
   *客户来源1
   */
  private String custSource;
  /**
   *总逾期次数占比
   */
  private String overdueRate;
  /**
   *近两年总逾期次数1
   */
  private String overdue;
  /**
   *最长逾期天数1
   */
  private String maxOverdue;
  /**
   *违法违规记录1
   */
  private String illegalRecord;
  /**
   *留钥匙1
   */
  private String leftKey;
  /**
   * 安装GPS1
   */
  private String GPS;

}
