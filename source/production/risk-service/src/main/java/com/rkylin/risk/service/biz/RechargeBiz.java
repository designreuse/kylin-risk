package com.rkylin.risk.service.biz;

import com.rkylin.risk.order.bean.SimpleOrder;

/**
 * 校验订单数据，评定等级
 * Created by cuixiaofang on 2016-3-25.
 */
public interface RechargeBiz {

    boolean synchRechargeResult(SimpleOrder order);

}
