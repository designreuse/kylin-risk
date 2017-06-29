package com.rkylin.risk.operation.bean;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;

/**
 * Created by lina on 2016-3-15.
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = {"customerName", "certificateId"})
public class ResultInfo implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 客户名称
   */
  private String customerName;
  /**
   * 证件号码
   */
  private String certificateId;
  /**
   * 返回码
   */
  private String resultCode;
  /**
   * 返回分数
   */
  private String resultScore;
  /**
   * 返回信息
   */
  private String resultMsg;
}
