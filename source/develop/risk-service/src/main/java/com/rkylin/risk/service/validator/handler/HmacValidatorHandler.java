package com.rkylin.risk.service.validator.handler;

import com.rkylin.risk.service.validator.core.ResultInfoProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by tomalloc on 16-4-14.
 */
@Slf4j
public abstract class HmacValidatorHandler implements ValidatorHandler<String> {
    private String hmac;
    private String code;
    private String message;

    public HmacValidatorHandler(String hmac, String code, String message) {
        this.hmac = hmac;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean handle(ResultInfoProxy proxy) {
        String strMd5hmac = DigestUtils.md5Hex(validateObject());
        log.info("【dubbo服务】风控系统签名字符串加密后:{}", strMd5hmac);
        if (!hmac.equals(strMd5hmac)) {
            proxy.setResultCode(code);
            proxy.setResultMessage(message);
            return false;
        }
        return true;
    }
}
