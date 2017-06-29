package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.mitou.api.OrderStatusChangeApi;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.api.ApiMitouConstants;
import com.rkylin.risk.service.bean.MitouCloseOrderRequestParam;
import com.rkylin.risk.service.bean.MitouResponseParam;
import com.rkylin.risk.service.biz.MitouBiz;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by chenfumin on 2017/1/13.
 */
@Slf4j
@Component("orderStatusChangeApi")
public class OrderStatusChangeApiImpl implements OrderStatusChangeApi {

  @Resource
  private MitouBiz mitouBiz;

  @Resource
  private OperateFlowService operateFlowService;

  @Resource
  private OrderService orderService;

  @Resource
  private ObjectMapper objectMapper;

  @Value("${mitou.platid}")
  private int platid;

  @Override
  public String updateOrderStatus(String orderNo, String status, String constid) {

    log.info("---------【米投更改订单状态服务】---------");
    log.info("---------接受参数：orderNo: {}, status: {}, constid:{}-------", orderNo, status, constid);

    if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(status) || StringUtils.isBlank(
        constid)) {
      return handleResult("1", "缺少必填入参");
    }

    try {

      // 检查是否有对应订单
      Order order = orderService.checkExistOrder(orderNo);

      if (order != null) {
        order.setStatusid(status);
        orderService.updateStatusid(order);

        String checkOrderId = order.getCheckorderid();
        OperateFlow operateFlow = operateFlowService.queryByCheckorderid(checkOrderId);

        if (operateFlow != null) {
          operateFlow.setResultstatus(status);
          operateFlowService.updateOperFlowStatus(operateFlow);
        }
      } else {
        log.info("----------未在风控查询到订单信息----------" + orderNo);
      }
    } catch (Exception e) {
      log.error("-------风控系统异常-------", e);
      return handleResult("1", "风控系统异常");
    }

    if (RuleConstants.XINGREN_CHANNEL_ID.equals(constid)) {
      MitouCloseOrderRequestParam param = new MitouCloseOrderRequestParam();
      param.setOrderNo(orderNo);
      param.setDataSource(ApiMitouConstants.MITOU_DATA_SOURCE_APP);

      try {
        MitouResponseParam responseParam = mitouBiz.closeOrder(platid, param);
        log.info("米投返回消息:{}", responseParam);
        if (responseParam != null && responseParam.getFlag() > 0) {
          return handleResult("0", "米投返回->" + responseParam.getMsg());
        }
      } catch (Exception e) {
        log.error("-------调用米投服务出错------", e);
        return handleResult("1", "米投服务异常");
      }
    }

    return handleResult("0", "关闭订单成功");
  }

  public String handleResult(String flag, String msg) {
    Result result = new Result(flag, msg);
    try {
      return objectMapper.writeValueAsString(result);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }

  @Setter
  @Getter
  @AllArgsConstructor
  private class Result {
    private String flag;
    private String msg;
  }
}
