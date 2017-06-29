package com.rkylin.risk.service.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lina on 2016-3-28.
 */
@Setter
@Getter
public class CustomerCode {
  /**
   * 年龄
   */
  private String age;
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
  private String resideTime;
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
  private String entryTime;
  /**
   * 月收入标准
   */
  private String wage;
  /**
   * 房产类型
   */
  private String houseType;
  /**
   * 房产面积
   */
  private String houseSize;
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
  private String otherEstate;
  /**
   * 租金比
   */
  private String rentRate;
  /**
   * 负债比
   */
  private String burdenRate;
  /**
   * 保证金比例
   */
  private String cashDepositRate;
  /**
   * 融资年限
   */
  private String financingTime;
  /**
   * 客户来源
   */
  private String custSource;
  /**
   * 总逾期次数占比
   */
  private String overdueRate;
  /**
   * 近两年总逾期次数
   */
  private String overdue;
  /**
   * 最长逾期天数
   */
  private String maxOverdue;
  /**
   * 违法违规记录
   */
  private String illegalRecord;
  /**
   * 留钥匙
   */
  private String leftKey;
  /**
   * 安装GPS26
   */
  private String GPS;
  /**
   * 是否禁入
   */
  private String isOff;
  /**
   * 禁入原因
   */
  private String msg;
}