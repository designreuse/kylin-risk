package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.CusFactorParam;

/**
 * Created by v-cuixiaofang on 2015/10/22.
 */
public interface CusFactorParamService {

    CusFactorParam queryByCustomerid(String customerid);

    CusFactorParam update(CusFactorParam cusFactorParam);

    CusFactorParam insertCusFactorParam(CusFactorParam cusFactorParam);

    Integer queryHighRiskbyUserId(String customerid);
}
