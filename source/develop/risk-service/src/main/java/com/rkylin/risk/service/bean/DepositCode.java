package com.rkylin.risk.service.bean;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 201508031790 on 2015/10/13.
 */
@Setter
@Getter
public class DepositCode implements Serializable {

  /**
   * 提现时间
   */
  private String deposittime;
  /**
   * 提现时间距离最近一笔充值请求时间间隔
   */
  private String interval;
  /**
   * 提现前充值频繁次数
   */
  private String times;
  /**
   * 大额提现
   */
  private String bigamount;
  /**
   * 除以5000是否为整数
   */
  private String div5000;
  /**
   * 该卡号前8位近1小时提现笔数异常
   */
  private String cardnumex;
  /**
   * 提现占比异常
   */
  private String depositpercent;
  /**
   * 历史高风险触发
   */
  private String historyrisk;
}
