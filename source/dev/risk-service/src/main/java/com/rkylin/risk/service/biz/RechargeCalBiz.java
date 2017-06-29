package com.rkylin.risk.service.biz;

import com.rkylin.risk.order.bean.SimpleOrder;

/**
 * Created by lina on 2016-6-23.
 */
public interface RechargeCalBiz {
  boolean synchRechargeResult(SimpleOrder order);

}
