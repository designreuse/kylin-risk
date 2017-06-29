package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.CusFactorParam;
import com.rkylin.risk.core.entity.Order;

/**
 * Created by cuixiaofang on 2016-6-27.
 */
public interface RiskScoreService {

    boolean insertDubiousTxn(Order order, CusFactorParam cusFactorParam, double scores);
}
