package com.rkylin.risk.core.dto;

import com.rkylin.risk.commons.entity.Amount;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 201508031790 on 2015/10/14.
 */
@Setter
@Getter
public class ScoreRuleBean {
  /**
   * 年龄
   */
  private Amount age;
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
  private Amount resideTime;
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
  private Amount entryTime;
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
  private Amount financingTime;
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
  private Amount overdue;
  /**
   * 最长逾期天数
   */
  private Amount maxOverdue;
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
  private String gps;

  /**
   * 交易时间异常
   */
  private Amount rechargetime;
  /**
   * 提现前充值频繁
   */
  private Amount rechcount;

  /**
   * 充值方式
   */
  private String rechway;

  /**
   * 提现时间
   */
  private Amount deposittime;
  /**
   * 提现时间距离最近一笔充值请求时间间隔
   */
  private Amount interval;
  /**
   * 提现前充值频繁次数
   */
  private Amount times;
  /**
   * 大额提现
   */
  private Amount bigamount;
  /**
   * 除以5000是否为整数
   */
  private String div5000;
  /**
   * 该卡号近1小时提现笔数异常
   */
  private Amount cardnumex;
  /**
   * 提现占比异常
   */
  private Amount depositpercent;
  /**
   * 历史高风险触发
   */
  private String historyrisk;




}
