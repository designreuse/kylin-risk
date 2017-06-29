package com.rkylin.risk.operation.bean;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ChenFumin on 2016-10-21.
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "checkorderid")
public class ReturnInfo implements Serializable {
  private static final long serialVersionUID = 1L;

  private String checkorderid;

  /**
   * 返回码
   */
  private String resultCode;

  /**
   * 返回信息
   */
  private String resultMsg;
}
