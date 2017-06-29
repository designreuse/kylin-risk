package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.CusFactorParam;

/**
 * Created by v-cuixiaofang on 2015/10/22.
 */
public interface CusFactorParamDao {

  CusFactorParam queryByCustomerid(String customerid);

  CusFactorParam insertCusFactorParam(CusFactorParam cusFactorParam);

  CusFactorParam update(CusFactorParam cusFactorParam);

  Integer queryHighRiskbyUserId(String customerid);

}
