package com.rkylin.risk.core.service;

import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.entity.Customerinfo;

/**
 * Created by ASUS on 2016-4-5.
 */
public interface FactorCustScoreService {
  double insertFactorCustomer(Customerinfo customerinfo, FactorCallBack callBack);

  void insertFactorCustomer(Customerinfo customerinfo, ResultBean scoreBean);
}
