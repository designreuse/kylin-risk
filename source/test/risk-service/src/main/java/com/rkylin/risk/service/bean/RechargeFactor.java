package com.rkylin.risk.service.bean;


import com.rkylin.risk.commons.entity.Amount;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * 交易评分模型----充值模型
 *
 */
@Setter
@Getter
public class RechargeFactor implements Serializable {
  /**
   * 交易时间异常
   */
  private int rechargetime;
  /**
   * 提现前充值频繁
   */
  private int rechcount;
  /**
   * 大额充值
   */
  private Amount bigamount;
  /**
   * 交易金额除以5000是否为整数
   */
  private boolean div5000;
  /**
   * 客户充值异常笔数
   */
  private int cardnumex;
  /**
   * 历史高风险触发
   */
  private boolean historyrisk;
  /**
   * 充值方式
   */
  private String rechway;




}
