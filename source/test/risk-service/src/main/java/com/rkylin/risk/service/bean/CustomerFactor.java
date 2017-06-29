package com.rkylin.risk.service.bean;

import com.rkylin.risk.commons.entity.Amount;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by 201508031790 on 2015/10/14.
 */
@Setter
@Getter
public class CustomerFactor implements Serializable {
  /**
   * 年龄
   */
  private Integer age;
  /**
   * 婚姻
   */
  private String marriage;
  /**
   * 户籍
   */
  private String registArea;
  /**
   * 本地居住时间
   */
  private Integer resideTime;
  /**
   * 文化程度
   */
  private String eduDegree;
  /**
   * 工作单位
   */
  private String comType;
  /**
   * 工作职位
   */
  private String position;
  /**
   * 入职时间
   */
  private Integer entryTime;
  /**
   * 月收入标准
   */
  private Amount wage;
  /**
   * 房产类型
   */
  private String houseType;
  /**
   * 房产面积
   */
  private Amount houseSize;
  /**
   * 房产地域
   */
  private String houseRegion;
  /**
   * 房产区域
   */
  private String houseArea;
  /**
   * 抵押情况
   */
  private String mortInfo;
  /**
   * 其他净资产折现
   */
  private Amount otherEstate;
  /**
   * 租金比
   */
  private Amount rentRate;
  /**
   * 负债比
   */
  private Amount burdenRate;
  /**
   * 保证金比例
   */
  private Amount cashDepositRate;
  /**
   * 融资年限
   */
  private Integer financingTime;
  /**
   * 客户来源
   */
  private String custSource;
  /**
   * 总逾期次数占比
   */
  private Amount overdueRate;
  /**
   * 近两年总逾期次数
   */
  private Integer overdue;
  /**
   * 最长逾期天数
   */
  private Integer maxOverdue;
  /**
   * 违法违规记录
   */
  private String illegalRecord;
  /**
   * 留钥匙
   */
  private String leftKey;
  /**
   * 安装GPS
   */
  private String GPS;
}
