package com.rkylin.risk.order.bean;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by lina on 2016-3-11.
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "orderId")
public class SimpleOrder implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * 内部订单号
   */
  private String orderId;

  /**
   * 工作流ID
   */
  private String checkorderid;

  /**
   * 业务类型ID
   */
  private String orderTypeId;

  /**
   * 订单日期
   */
  private String orderDate;

  /**
   * 订单时间
   */
  private String orderTime;

  /**
   * 机构代码
   */
  private String rootInstCd;

  /**
   * 产品ID
   */
  private String productId;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 授信提供方ID
   */
  private String providerId;

  /**
   * 用户订单关联流水号
   */
  private String userOrderId;

  /**
   * 关联用户ID
   */
  private String userRelateId;

  /**
   * 交易金额
   */
  private String amount;

  /**
   * 备注
   */
  private String remark;

  /**
   * 订单控制
   */
  private String orderControl;

  /**
   * 应答码
   */
  private String respCode;

  /**
   * 订单状态
   */
  private String statusId;

  /**
   * 商品名称
   */
  private String goodsName;

  /**
   * 商品描述
   */
  private String goodsDetail;

  /**
   * 商品数量
   */
  private String goodsCnt;

  /**
   * 商品单价
   */
  private String unitPrice;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 银行卡号
   */
  private String cardNum;

  /**
   * 身份证
   */
  private String identityCard;

  /**
   * 业务数据（JSON串(申请期限,申请者单位名称,费率模板ID)）
   */
  private String svcString;

  /**
   * 逾期率
   */
  private String overdueRate;

  //业务ID  杏仁时传婚纱、美容、旅游等业务ID
  private String svcId;

  //关联用户名称 （机构名称）
  private String userRelateName;


}
