package com.rkylin.risk.operation.api;

import com.rkylin.risk.operation.bean.ReturnInfo;

/**
 * 工作流状态同步 Created by ChenFumin on 2016/10/20.
 */
public interface OperateFlowStatusApi {
  /**
   * 工作流状态同步
   * @param checkorderid
   * @param status
   * @param reason
   * @param hmac
   * @return
   */
  public ReturnInfo checkorderStatus(String checkorderid, String status, String reason,
      String hmac);
}
