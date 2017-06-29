package com.rkylin.risk.service.biz;

import com.rkylin.risk.operation.bean.CustomerMsg;
import com.rkylin.risk.operation.bean.ResultInfo;
import com.rkylin.risk.service.bean.CustomerCode;

/**
 * Created by 201508240185 on 2015/10/16.
 */
public interface CustomerBiz {
    CustomerCode getCustomerFactorCode(CustomerMsg customer);

    /**
     * 客户信息处理
     * @param customerMsg
     * @return
     */
    ResultInfo customerInfoHandle(CustomerMsg customerMsg);
}
