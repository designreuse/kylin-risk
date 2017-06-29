package com.rkylin.risk.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomalloc on 16-12-12.
 */
@Setter
@Getter
public class CreditRequestEntity {
  /**
   * 姓名
   */
  private String name;
  /**
   * 手机号
   */
  private String mobile;

  /**
   * 身份证号
   */
  private String idNumber;

  /**
   * 银行卡号
   */
  private String bankCard;

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



}
