package com.rkylin.risk.service.validator.handler;

import com.rkylin.risk.service.validator.core.ResultInfoProxy;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by cuixiaofang on 2016-5-18.
 */
@Slf4j
public abstract class RootInstCdValidatorHandler implements ValidatorHandler<String> {
    private String rootInstCD;

    private String code;

    private String message;

    public RootInstCdValidatorHandler(String rootInstCD, String code, String message) {
        this.rootInstCD = rootInstCD;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean handle(ResultInfoProxy proxy) {
        log.info("【dubbo服务】风控系统订单机构代码:{}", rootInstCD);
        if (!"M000005".equals(rootInstCD)) {
            proxy.setResultCode(code);
            proxy.setResultMessage(message);
            return false;
        }
        return true;
    }
}
