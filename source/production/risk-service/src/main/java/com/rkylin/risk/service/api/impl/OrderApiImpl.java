package com.rkylin.risk.service.api.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Dubioustxn;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.DubioustxnService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.order.api.OrderApi;
import com.rkylin.risk.order.bean.ResultInfo;
import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.biz.DepositBiz;
import com.rkylin.risk.service.biz.LoanDealBiz;
import com.rkylin.risk.service.biz.RechargeBiz;
import com.rkylin.risk.service.enumtype.OrderType;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;
import static com.rkylin.risk.core.utils.ObjectUtils.intValueOf;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by lina on 2016-3-11.
 */
@Component("orderApi")
@Slf4j
public class OrderApiImpl implements OrderApi {
  @Resource
  private RechargeBiz rechargeBiz;
  @Resource
  private DepositBiz depositBiz;
  @Resource
  private LoanDealBiz loanDealBiz;
  @Resource
  private OrderService orderService;
  @Resource
  private DubioustxnService dubioustxnService;

  private ResultInfo newFailResultInfo(String message) {
    ResultInfo resultInfo = new ResultInfo();
    resultInfo.setResultCode("1");
    resultInfo.setResultMsg(message);
    return resultInfo;
  }

  @Override
  public ResultInfo ordercheck(SimpleOrder simpleOrder, String hmac) {
    long staTimeMillis = DateTime.now().getMillis();
    log.info("request data:{},hmac:{}", simpleOrder, hmac);
    if (simpleOrder == null) {
      return newFailResultInfo("【风控系统】该订单为空! ");
    }
    if (isBlank(hmac)) {
      return newFailResultInfo("【风控系统】签名字符串为空!");
    }
    if (checkOrderField(simpleOrder)) {
      return newFailResultInfo("【风控系统】订单中有一个以上字段为空,请检查订单数据!");
    }

    //校验签名
    String hmacString = new StringBuilder(simpleOrder.getOrderId())
        .append(simpleOrder.getOrderTypeId())
        .append(simpleOrder.getRootInstCd())
        .append(simpleOrder.getProductId())
        .append(simpleOrder.getUserId())
        .append(ApiServiceConstants.ORDER_API_HMAC)
        .toString();
    if (!hmac.equals(DigestUtils.md5Hex(hmacString))) {
      return newFailResultInfo("【风控系统】签名校验失败!");
    }
    try {
      //业务处理
      String subOrderTypeId = null;
      if (simpleOrder.getOrderTypeId() != null && simpleOrder.getOrderTypeId().length()
          > 1) {
        subOrderTypeId = simpleOrder.getOrderTypeId().substring(0, 2);
      }
      boolean isPass;
      //充值交易
      if (OrderType.B1.getValue().equals(subOrderTypeId)
          && "M000005".equals(simpleOrder.getRootInstCd())) {
        isPass = rechargeBiz.synchRechargeResult(simpleOrder);
        //提现交易
      } else if (OrderType.B2.getValue().equals(subOrderTypeId)
          && "M000005".equals(simpleOrder.getRootInstCd())) {
        isPass = depositBiz.synchDepositResult(simpleOrder);
        //贷款
      } else if ("M20001".equals(simpleOrder.getOrderTypeId())) {
        isPass = loanDealBiz.handlingLoanDeal(simpleOrder);
      } else {
        log.info("【dubbo服务】风控系统此订单无校验");
        Order order = insertOrder(simpleOrder);
        log.info("【dubbo服务】风控系统插入订单表数据：-->"
            + order);
        return newSuccessResultInfo("【风控系统】此订单无校验");
      }
      if (isPass) {
        return newSuccessResultInfo("【风控系统】订单交易-->校验通过");
      } else {
        return newFailResultInfo("【风控系统】校验该笔订单交易为高风险交易");
      }
    } catch (Exception e) {
      log.info("系统错误，错误信息:{}", e);
      return exceptionResultInfo("系统发生错误，请联系管理员");
    } finally {
      log.info("风控订单流程checkorderid：{}，-------------------》{}", simpleOrder.getCheckorderid(),
          DateTime.now().getMillis() - staTimeMillis);
    }
  }

  private ResultInfo newSuccessResultInfo(String message) {
    ResultInfo resultInfo = new ResultInfo();
    resultInfo.setResultCode("0");
    resultInfo.setResultMsg(message);
    return resultInfo;
  }

  private ResultInfo exceptionResultInfo(String message) {
    ResultInfo resultInfo = new ResultInfo();
    resultInfo.setResultCode("99");
    resultInfo.setResultMsg(message);
    return resultInfo;
  }

  /**
   * 检查订单字段
   */
  private boolean checkOrderField(SimpleOrder simpleOrder) {
    if (isEmpty(simpleOrder.getOrderId())) {
      log.info("订单字段orderId为空");
      return true;
    }
    if (isEmpty(simpleOrder.getOrderTypeId())) {
      log.info("订单字段orderType为空");
      return true;
    }
    if (isEmpty(simpleOrder.getRootInstCd())) {
      log.info("订单字段RootInstCd为空");
      return true;
    }
    if (isEmpty(simpleOrder.getProductId())) {
      log.info("订单字段productId为空");
      return true;
    }
    if (isEmpty(simpleOrder.getUserId())) {
      log.info("订单字段userId为空");
      return true;
    }
    return false;
  }

  /**
   * 插入订单表
   */
  private Order insertOrder(SimpleOrder simpleOrder) {
    // 判断订单是否存在
    Order oldOrder = orderService.checkExistOrder(simpleOrder.getOrderId());
    if (oldOrder != null) {
      Dubioustxn dubioustxn = dubioustxnService.queryByTxnum(simpleOrder.getOrderId());
      if (dubioustxn != null && dubioustxn.getRisklevel().equals(Constants.HIGH_LEVEL)) {
        oldOrder.setDubioustxnid(dubioustxn.getId());
        orderService.update(oldOrder);
      }
      return oldOrder;
    }
    Order order = new Order();
    order.setOrderid(simpleOrder.getOrderId());
    order.setCheckorderid(simpleOrder.getCheckorderid());
    order.setOrdertypeid(simpleOrder.getOrderTypeId());
    log.info("【dubbo服务】风控系统接收订单日期为:{}和时间:{}", simpleOrder.getOrderDate(),
        simpleOrder.getOrderTime());
    order.setOrderdate(
        LocalDate.parse(simpleOrder.getOrderDate(), DateTimeFormat.forPattern("yyyyMMdd")));
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    order.setOrdertime(DateTime.parse(simpleOrder.getOrderTime(), format));
    order.setRootinstcd(simpleOrder.getRootInstCd());
    order.setProductid(simpleOrder.getProductId());
    order.setUserid(simpleOrder.getUserId());
    order.setUserrelateid(simpleOrder.getUserRelateId());
    order.setProviderid(simpleOrder.getProviderId());
    order.setUserorderid(simpleOrder.getUserOrderId());
    order.setAmount(amountValueOf(simpleOrder.getAmount(), null));
    order.setMobile(simpleOrder.getMobile());
    order.setCardNum(simpleOrder.getCardNum());
    order.setIdentityCard(simpleOrder.getIdentityCard());
    order.setOrdercontrol(simpleOrder.getOrderControl());
    order.setRespcode(simpleOrder.getRespCode());
    order.setStatusid(simpleOrder.getStatusId());
    order.setGoodsname(simpleOrder.getGoodsName());
    order.setGoodsdetail(simpleOrder.getGoodsDetail());
    order.setGoodscnt(intValueOf(simpleOrder.getGoodsCnt(), null));
    order.setUnitprice(amountValueOf(simpleOrder.getUnitPrice(), null));
    return orderService.insert(order);
  }
}
