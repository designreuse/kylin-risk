package com.rkylin.risk.service.biz;

import com.rkylin.risk.order.bean.SimpleOrder;

/**
 *
 * 提现交易类订单
 * @author cuixiaofang
 *
 */
public interface DepositBiz {

    boolean synchDepositResult(SimpleOrder simpleOrder);

}
