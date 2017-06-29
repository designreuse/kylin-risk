package com.rkylin.risk.service.bean;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 201508031790 on 2015/10/14.
 */
@Setter
@Getter
public class RechargeCode implements Serializable {

  /**
   * 交易时间异常
   */
  private String rechargetime;
  /**
   * 提现前充值频繁
   */
  private String rechcount;
  /**
   * 大额充值
   */
  private String bigamount;
  /**
   * 交易金额除以5000是否为整数
   */
  private String div5000;
  /**
   * 客户充值异常笔数
   */
  private String cardnumex;
  /**
   * 历史高风险触发
   */
  private String historyrisk;
  /**
   * 充值方式
   */
  private String rechway;
}
