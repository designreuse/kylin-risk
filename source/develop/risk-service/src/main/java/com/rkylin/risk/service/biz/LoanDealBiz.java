package com.rkylin.risk.service.biz;

import com.rkylin.risk.order.bean.SimpleOrder;

/**
 * 处理放款订单
 *
 * Created by cuixiaofang on 2016-7-18.
 */
public interface LoanDealBiz {

    boolean handlingLoanDeal(SimpleOrder simpleOrder);

}
