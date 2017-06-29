package com.rkylin.risk.order.bean;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by lina on 2016-3-15.
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "orderId")
public class ResultInfo implements Serializable {
  private static final long serialVersionUID = 1L;
  //内部订单号
  private String orderId;
  //返回码
  private String resultCode;
  //返回信息
  private String resultMsg;
}
