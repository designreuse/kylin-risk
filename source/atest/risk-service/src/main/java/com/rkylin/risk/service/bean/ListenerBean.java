package com.rkylin.risk.service.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lina on 2016-8-22.
 */
@Setter
@Getter
public class ListenerBean {
  private String checkorderid;
  /*
  状态
   */
  private String status;

  /*
  续议原因
   */
  private String reason;

  /*
  风险提示
   */
  private String riskmsg;

  private String bussinesstype;
}
