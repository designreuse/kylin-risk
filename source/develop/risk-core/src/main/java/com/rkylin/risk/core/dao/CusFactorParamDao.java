package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.CusFactorParam;

/**
 * Created by v-cuixiaofang on 2015/10/22.
 */
public interface CusFactorParamDao {

  CusFactorParam queryByCustomerid(String customerid);

  CusFactorParam queryByCustomerid(String customerid, String orderTime);

  CusFactorParam updateByCustid(CusFactorParam cusFactorParam);

  CusFactorParam insertCusFactorParam(CusFactorParam cusFactorParam);

  CusFactorParam updateBycustomerid(CusFactorParam cusFactorParam);

  Integer queryHighRiskbyUserId(String customerid);

  CusFactorParam updateByUserIdAndTime(CusFactorParam cusFactorParam);
}
