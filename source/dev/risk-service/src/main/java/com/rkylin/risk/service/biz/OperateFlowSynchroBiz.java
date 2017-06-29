package com.rkylin.risk.service.biz;

/**
 * Created by ChenFumin on 2016-10-24.
 */
public interface OperateFlowSynchroBiz {

  String synchroStatus(String checkorderid, String status, String reason);

}
