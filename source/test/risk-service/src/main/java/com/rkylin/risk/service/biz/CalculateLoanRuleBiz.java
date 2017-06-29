package com.rkylin.risk.service.biz;

import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.entity.Merchant;
import com.rkylin.risk.order.bean.SimpleOrder;

/**
 * 根据规则引擎判定贷款订单
 *
 * Created by cuixiaofang on 2016-7-21.
 */
public interface CalculateLoanRuleBiz {

    LogicRuleBean calculateLoanRule(SimpleOrder simpleOrder, Merchant merchant);
}
