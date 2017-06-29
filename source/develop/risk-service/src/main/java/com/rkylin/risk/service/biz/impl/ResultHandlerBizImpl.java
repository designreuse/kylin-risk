package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.service.biz.ResultHandlerBiz;
import org.springframework.stereotype.Component;

/**
 * Created by cuixiaofang on 2016-7-28.
 */
@Component("resultHandlerBiz")
public class ResultHandlerBizImpl implements ResultHandlerBiz {

    @Override
    public boolean resultHandler() {
        return false;
    }
}
