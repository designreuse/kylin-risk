package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.CusFactorParam;
import com.rkylin.risk.core.entity.Order;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/24 version: 1.0
 */
public interface FactorScoreService {
  boolean insertDubiousTxn(Order order, CusFactorParam cusFactorParam,
      FactorCallBack callBack);
}
