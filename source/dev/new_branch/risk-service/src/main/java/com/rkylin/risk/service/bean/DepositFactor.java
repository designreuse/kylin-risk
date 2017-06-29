package com.rkylin.risk.service.bean;

import com.rkylin.risk.commons.entity.Amount;

import java.io.Serializable;

/**
 * Created by 201508031790 on 2015/10/13.
 */
public class DepositFactor implements Serializable {

  /**
   *提现时间
   */
  private int deposittime;
  /**
   *提现时间距离最近一笔充值请求时间间隔
   */
  private Amount interval;
  /**
   *提现前充值频繁次数
   */
  private int times;
  /**
   *大额提现
   */
  private Amount bigamount;
  /**
   *除以5000是否为整数
   */
  private boolean div5000;
  /**
   *该卡号近1小时提现笔数异常
   */
  private int cardnumex;
  /**
   *提现占比异常
   */
  private Amount depositpercent;
  /**
   *历史高风险触发
   */
  private boolean historyrisk;

  public int getDeposittime() {
    return deposittime;
  }

  public void setDeposittime(int deposittime) {
    this.deposittime = deposittime;
  }

  public Amount getInterval() {
    return interval;
  }

  public void setInterval(Amount interval) {
    this.interval = interval;
  }

  public int getTimes() {
    return times;
  }

  public void setTimes(int times) {
    this.times = times;
  }

  public Amount getBigamount() {
    return bigamount;
  }

  public void setBigamount(Amount bigamount) {
    this.bigamount = bigamount;
  }

  public boolean getDiv5000() {
    return div5000;
  }

  public void setDiv5000(boolean div5000) {
    this.div5000 = div5000;
  }

  public int getCardnumex() {
    return cardnumex;
  }

  public void setCardnumex(int cardnumex) {
    this.cardnumex = cardnumex;
  }

  public Amount getDepositpercent() {
    return depositpercent;
  }

  public void setDepositpercent(Amount depositpercent) {
    this.depositpercent = depositpercent;
  }

  public boolean getHistoryrisk() {
    return historyrisk;
  }

  public void setHistoryrisk(boolean historyrisk) {
    this.historyrisk = historyrisk;
  }
}
