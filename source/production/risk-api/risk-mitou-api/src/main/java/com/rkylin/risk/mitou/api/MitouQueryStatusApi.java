package com.rkylin.risk.mitou.api;

/**
 * 查询米投状态API
 * Created by chenfumin on 2016/12/22.
 */
public interface MitouQueryStatusApi {

  /**
   * 查询米投手机和电商授权状态API
   * @param data json串 {"userid":"1001","constid","JG001"}
   * @return {"mobileAuthStatus":"true","onlineRetailerStatus":"true"}
   */
  String queryAuthStatus(String data);
}
