package com.rkylin.risk.operation.api;

/**
 * Created by lina on 2016-8-15.
 */
public interface MerchantCheckApi {
  /**
   * 查询机构准入审核结果
   */
  String checkMerchantMsg(String checkorderid);

  /*
  查询机构预算年培训收入、预算年培训人数
   */
  String calcMerchantMsg(String checkorderid);
}
