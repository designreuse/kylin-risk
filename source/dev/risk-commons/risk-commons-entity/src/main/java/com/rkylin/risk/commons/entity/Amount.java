package com.rkylin.risk.commons.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Amount implements Serializable {
  /**
   * 金额值，默认单位为元
   */
  protected BigDecimal value;

  /**
   * 精度
   */
  protected int accuracy;

  /**
   * 默认精度
   */
  private static final int DEFAULT_ACCURACY = 2;

  /**
   * 允许的最小值
   */
  private static final BigDecimal MIN_VALUE = new BigDecimal(-10000000000000L);

  /**
   * 允许的最大值
   */
  private static final BigDecimal MAX_VALUE = new BigDecimal(10000000000000L);

  public static final Amount ZERO = new Amount(BigDecimal.ZERO);

  /**
   * 无参构造器
   */
  public Amount() {
    this(BigDecimal.ZERO, DEFAULT_ACCURACY);
  }

  /**
   * 不带精度的构造器，默认两位精度
   */
  public Amount(BigDecimal value) {
    this(value, DEFAULT_ACCURACY);
  }

  /**
   * 不带精度的构造器，默认两位精度
   */
  public Amount(long value) {
    this(BigDecimal.valueOf(value), DEFAULT_ACCURACY);
  }

  /**
   * 带精度的构造器
   */
  public Amount(long value, int accuracy) {
    this(BigDecimal.valueOf(value), accuracy);
  }

  /**
   * 不带精度的构造器，默认两位精度
   */
  public Amount(double value) {
    this(BigDecimal.valueOf(value), DEFAULT_ACCURACY);
  }

  /**
   * 带精度的构造器
   */
  public Amount(double value, int accuracy) {
    this(BigDecimal.valueOf(value), accuracy);
  }

  /**
   * 不带精度的构造器，默认两位精度
   */
  public Amount(int value) {
    this(BigDecimal.valueOf(value), DEFAULT_ACCURACY);
  }

  /**
   * 带精度的构造器
   */
  public Amount(int value, int accuracy) {
    this(BigDecimal.valueOf(value), accuracy);
  }

  /**
   * 不带精度的构造器，默认两位精度
   */
  public Amount(String value) {
    this(new BigDecimal(value), DEFAULT_ACCURACY);
  }

  /**
   * 带精度的构造器
   */
  public Amount(String value, int accuracy) {
    this(new BigDecimal(value), accuracy);
  }

  /**
   * 带精度的构造器
   */
  public Amount(BigDecimal value, int accuracy) {
    // 检查金额数值范围是否合法
    if (value == null || value.compareTo(MIN_VALUE) < 0
        || value.compareTo(MAX_VALUE) > 1) {
      throw new IllegalArgumentException("无效的金额数值[value=" + value + "]");
    }
    // 将金额数值从第N+1位四舍五入，保留N位小数
    this.accuracy = accuracy;
    this.value = value.setScale(this.accuracy, BigDecimal.ROUND_HALF_UP);
  }

  /**
   * 判断本金额是否大于某金额
   */
  public final boolean isGreaterThan(Amount amount) {
    return value.compareTo(amount.getValue()) > 0;
  }

  public final boolean isGreaterThan(int amount) {
    return value.compareTo(new BigDecimal(amount)) > 0;
  }

  public final boolean isGreaterThan(long amount) {
    return value.compareTo(new BigDecimal(amount)) > 0;
  }

  public final boolean isGreaterThan(double amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) > 0;
  }

  public final boolean isGreaterOrEqualThan(Amount amount) {
    return value.compareTo(amount.getValue()) >= 0;
  }

  public final boolean isGreaterOrEqualThan(int amount) {
    return value.compareTo(new BigDecimal(amount)) >= 0;
  }

  public final boolean isGreaterOrEqualThan(double amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) >= 0;
  }

  public final boolean isGreaterOrEqualThan(long amount) {
    return value.compareTo(new BigDecimal(amount)) >= 0;
  }

  /**
   * 判断本金额是否等于某金额
   */
  public final boolean isEqualTo(Amount amount) {
    return value.compareTo(amount.getValue()) == 0;
  }

  public final boolean isEqualTo(int amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) == 0;
  }

  public final boolean isEqualTo(long amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) == 0;
  }

  public final boolean isEqualTo(double amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) == 0;
  }

  /**
   * 判断本金额是否小于某金额
   */
  public final boolean isLesserThan(Amount amount) {
    return value.compareTo(amount.getValue()) < 0;
  }

  public final boolean isLesserThan(int amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) < 0;
  }

  public final boolean isLesserThan(long amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) < 0;
  }

  public final boolean isLesserThan(double amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) < 0;
  }

  public final boolean isLesserOrEqualThan(Amount amount) {
    return value.compareTo(amount.getValue()) <= 0;
  }

  public final boolean isLesserOrEqualThan(int amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) <= 0;
  }

  public final boolean isLesserOrEqualThan(long amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) <= 0;
  }

  public final boolean isLesserOrEqualThan(double amount) {
    return value.compareTo(BigDecimal.valueOf(amount)) <= 0;
  }

  public final int compareTo(Amount amount) {
    return value.compareTo(amount.getValue());
  }

  public final int compareTo(int amount) {
    return value.compareTo(BigDecimal.valueOf(amount));
  }

  public final int compareTo(long amount) {
    return value.compareTo(BigDecimal.valueOf(amount));
  }

  public final int compareTo(double amount) {
    return value.compareTo(BigDecimal.valueOf(amount));
  }

  /**
   * 判断本金额是否为正数
   */
  public final boolean isPositive() {
    return value.compareTo(BigDecimal.ZERO) > 0;
  }

  /**
   * 判断本金额是否为0
   */
  public final boolean isZero() {
    return value.compareTo(BigDecimal.ZERO) == 0;
  }

  /**
   * 判断本金额是否为负数
   */
  public final boolean isNegative() {
    return value.compareTo(BigDecimal.ZERO) < 0;
  }

  /**
   * 金额相加
   *
   * @param amount 被加金额
   */
  public final Amount add(Amount amount) {
    return new Amount(this.value.add(amount.getValue()));
  }

  public final Amount add(double amount) {
    return new Amount(this.value.add(BigDecimal.valueOf(amount)));
  }

  /**
   * 金额相减
   *
   * @param amount 被减金额
   */
  public final Amount subtract(Amount amount) {
    return new Amount(this.value.subtract(amount.getValue()));
  }

  /**
   * 金额乘以倍数
   *
   * @param multiple 倍数
   */
  public final Amount multiply(long multiple) {
    return new Amount(this.value.multiply(BigDecimal.valueOf(multiple)));
  }

  public final Amount multiply(Amount multiple) {
    return new Amount(this.value.multiply(multiple.getValue()));
  }

  /**
   * 金额乘以倍数
   *
   * @param multiple 倍数
   */
  public final Amount multiply(int multiple) {
    return new Amount(this.value.multiply(BigDecimal.valueOf(multiple)));
  }

  /**
   * 金额除以倍数
   *
   * @param divide 倍数
   */
  public final Amount divide(int divide) {
    return new Amount(this.value.divide(new BigDecimal(divide)));
  }

  /**
   * 金额除以倍数
   *
   * @param divide 倍数
   */
  public final Amount divide(double divide) {
    return new Amount(this.value.divide(BigDecimal.valueOf(divide)));
  }

  /**
   * 金额除以倍数
   *
   * @param divide 倍数
   */
  public final Amount divide(Amount divide) {
    return new Amount(this.value.divide(divide.getValue()));
  }

  public final Amount divide(Amount divide, int scale, RoundingMode roundingMode) {
    return new Amount(this.value.divide(divide.getValue(), scale, roundingMode));
  }

  /**
   * 金额乘以倍数
   *
   * @param multiple 倍数
   */
  public final Amount multiply(double multiple) {
    return new Amount(this.value.multiply(BigDecimal.valueOf(multiple)));
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public void setAccuracy(int accuracy) {
    this.accuracy = accuracy;
  }

  /**
   * 获取金额值
   */
  public BigDecimal getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Amount other = (Amount) obj;
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return value != null ? value.toString() : "";
  }
}
