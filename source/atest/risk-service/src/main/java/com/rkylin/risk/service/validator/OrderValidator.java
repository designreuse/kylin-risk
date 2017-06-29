package com.rkylin.risk.service.validator;

import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.validator.builder.OrderResultInfoBuilder;
import com.rkylin.risk.service.validator.core.ResultInfoProxy;
import com.rkylin.risk.service.validator.core.ValidateResult;
import com.rkylin.risk.service.validator.core.Validator;
import com.rkylin.risk.service.validator.handler.BlankValidatorHandler;
import com.rkylin.risk.service.validator.handler.EmptyValidatorHandler;
import com.rkylin.risk.service.validator.handler.Handler;
import com.rkylin.risk.service.validator.handler.HmacValidatorHandler;
import com.rkylin.risk.service.validator.handler.NullValidatorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by tomalloc on 16-4-5.
 */
@Component("orderValidator")
@Slf4j
public class OrderValidator implements ServiceValidator<SimpleOrder> {

    @Override
    public ValidateResult<OrderResultInfoBuilder> validate(final SimpleOrder simpleOrder,
                                                           final String hmac) {
        final OrderResultInfoBuilder resultInfoBuilder = OrderResultInfoBuilder.builder();
        ResultInfoProxy proxy = new ResultInfoProxy() {
            @Override
            public void setResultCode(String code) {
                resultInfoBuilder.resultCode(code);
            }

            @Override
            public void setResultMessage(String message) {
                resultInfoBuilder.resultMsg(message);
            }
        };
        return Validator.validator(resultInfoBuilder)
                .on(new NullValidatorHandler<SimpleOrder>(simpleOrder, "99", "订单为空!"))
                .on(new Handler() {
                    @Override
                    public boolean handle(ResultInfoProxy proxy) {
                        log.info("【dubbo服务】风控系统订单交易订单号:{}", simpleOrder.getOrderId());
                        resultInfoBuilder.orderId(simpleOrder.getOrderId());
                        return true;
                    }
                })
                .on(new BlankValidatorHandler("99", "签名字符串为空!") {
                    @Override
                    public String validateObject() {
                        log.info("【dubbo服务】风控系统订单平台签名字符串参数:{}", hmac);
                        return hmac;
                    }
                })
                .on(new EmptyValidatorHandler("99", "订单的订单号为空!") {
                    @Override
                    public String validateObject() {
                        return simpleOrder.getOrderId();
                    }
                })
                .on(new EmptyValidatorHandler("99", "订单状态为空!") {
                    @Override
                    public String validateObject() {
                        log.info("【dubbo服务】风控系统订单状态:{}", simpleOrder.getStatusId());
                        return simpleOrder.getStatusId();
                    }
                }).on(new HmacValidatorHandler(hmac, "99", "签名校验失败!") {
                    @Override
                    public String validateObject() {
                        return new StringBuilder(simpleOrder.getOrderId())
                                .append(simpleOrder.getStatusId())
                                .append(ApiServiceConstants.ORDER_API_HMAC).toString();
                    }
                })
                .doValidate(proxy);
    }
}
