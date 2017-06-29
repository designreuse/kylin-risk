package com.rkylin.risk.operation.api;

import com.rkylin.risk.operation.bean.CustomerMsg;
import com.rkylin.risk.operation.bean.ResultInfo;

/**
 * Created by lina on 2016-4-5.
 * 获取运营系统数据
 */
public interface OperationApi {

    /**
     * 客户评分接口
     * @param customerMsg
     * @return
     */
    ResultInfo customerinfo(CustomerMsg customerMsg, String hmac);

}
