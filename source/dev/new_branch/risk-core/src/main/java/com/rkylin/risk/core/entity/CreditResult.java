package com.rkylin.risk.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rkylin.risk.commons.entity.Amount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

/**
 * Created by tomalloc on 16-8-23.
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class CreditResult implements Entity {
  @JsonIgnore
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
  private String creditProduct;
  /**
   * 模块
   */
  @JsonIgnore
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
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private DateTime requestTime;

  /**
   * 征信系统返回的唯一响应标示
   */
  private String responseId;

  /**
   * 查询者
   */
  private String querier;


  /**
   * 渠道
   */
  private String channel;

  /**
   * 工作流ID
   */
  private String workflowId;

  /**
   * 订单ID
   */
  private String orderId;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 单价
   */
  private Amount price;
}
