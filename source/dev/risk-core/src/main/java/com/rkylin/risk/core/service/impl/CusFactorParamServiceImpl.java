package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CusFactorParamDao;
import com.rkylin.risk.core.entity.CusFactorParam;
import com.rkylin.risk.core.service.CusFactorParamService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by v-cuixiaofang on 2015/10/22.
 */
@Service("cusFactorParamService")
public class CusFactorParamServiceImpl implements CusFactorParamService {

  @Resource
  private CusFactorParamDao cusFactorParamDao;

  @Override
  public CusFactorParam queryByCustomerid(String customerid) {
    if (isNotEmpty(customerid)) {
      return cusFactorParamDao.queryByCustomerid(customerid);
    }
    return null;
  }

  @Override
  public CusFactorParam update(CusFactorParam cusFactorParam) {
    return cusFactorParamDao.update(cusFactorParam);
  }

  @Override
  public CusFactorParam insertCusFactorParam(CusFactorParam cusFactorParam) {
    return cusFactorParamDao.insertCusFactorParam(cusFactorParam);
  }

  @Override
  public Integer queryHighRiskbyUserId(String customerid) {
    if (isNotEmpty(customerid)) {
      return cusFactorParamDao.queryHighRiskbyUserId(customerid);
    }
    return null;
  }
}
