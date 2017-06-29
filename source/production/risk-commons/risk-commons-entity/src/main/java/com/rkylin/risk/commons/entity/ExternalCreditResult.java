package com.rkylin.risk.commons.entity;

import com.rkylin.risk.commons.enumtype.CreditProductType;
import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-8-23.
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class ExternalCreditResult implements Serializable {
  private long id;
  /**
   * 用户名
   */
  private String userName;
  /**
   * 身份证号
   */
  private String idNumber;
  /**
   * 手机号
   */
  private String mobile;
  /**
   * 银行卡号
   */
  private String bankCard;
  /**
   * 产品
   */
  private CreditProductType creditProduct;
  /**
   * 模块
   */
  private long creditModuleId;
  /**
   * 结果
   */
  private String result;
  /**
   * 征信系统的返回状态码
   */
  private String creditCode;

  /**
   * 风控系统的返回码
   */
  private String riskCode;

  /**
   * 请求时间
   */
  private Date requestTime;

  /**
   * 征信系统返回的唯一响应标示
   */
  private String responseId;
}
