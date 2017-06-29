package com.rkylin.risk.service.biz.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.service.FactorCallBack;
import com.rkylin.risk.core.service.FactorCustScoreService;
import com.rkylin.risk.core.utils.BeanMappper;
import com.rkylin.risk.kie.spring.factorybeans.KieContainerSession;
import com.rkylin.risk.operation.bean.CustomerMsg;
import com.rkylin.risk.operation.bean.ResultInfo;
import com.rkylin.risk.service.bean.CustomerCode;
import com.rkylin.risk.service.bean.CustomerFactor;
import com.rkylin.risk.service.biz.CustomerBiz;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;
import static com.rkylin.risk.core.utils.ObjectUtils.intValueOf;

/**
 * Created by 201508240185 on 2015/10/15.
 */
@Slf4j
@Component("customerBiz")
public class CustomerBizImpl implements CustomerBiz {

  @Resource
  private FactorCustScoreService factorCustScoreService;

  @Resource
  KieContainerSession kieContainerSession;


  /**
   * 调用drools文件进行计算
   */
  @Override
  public CustomerCode getCustomerFactorCode(CustomerMsg customer) {
    CustomerFactor factor = convertCustomerFactor(customer);
    KieContainer kieContainer =
        kieContainerSession.getBean("com.rkylin.risk.rule:risk-rule-repository");
    KieSession customerKsession = kieContainer.newKieSession("customerKsession");
    CustomerCode code = new CustomerCode();
    customerKsession.insert(code);
    customerKsession.insert(factor);
    customerKsession.fireAllRules();
    if (customerKsession.getObjects().size() == 1) {
      code.setIsOff("true");
    }
    customerKsession.destroy();
    return code;
  }

  /**
   * 客户评分业务处理
   */
  @Override
  public ResultInfo customerInfoHandle(final CustomerMsg customerMsg) {
    //customerMsg转换为Customerinfo
    Customerinfo customerinfo = convertCustomerinfo(customerMsg);

    //调用规则处理，返回一个评分结果
    FactorCallBack factorCallBack = new FactorCallBack() {
      boolean isoff = true;
      String offmsg = "";

      @Override
      public List<String> doGetFactor() {
        CustomerCode code = getCustomerFactorCode(customerMsg);
        List<String> list = Lists.newArrayList();
        if (!"true".equals(code.getIsOff())) {
          list.add(code.getAge());
          list.add(code.getBurdenRate());
          list.add(code.getCashDepositRate());
          list.add(code.getComType());
          list.add(code.getCustSource());
          list.add(code.getEduDegree());
          list.add(code.getEntryTime());
          list.add(code.getFinancingTime());
          list.add(code.getGPS());
          list.add(code.getHouseArea());
          list.add(code.getHouseRegion());
          list.add(code.getHouseSize());
          list.add(code.getHouseType());
          list.add(code.getIllegalRecord());
          list.add(code.getLeftKey());
          list.add(code.getMarriage());
          list.add(code.getMaxOverdue());
          list.add(code.getMortInfo());
          list.add(code.getOtherEstate());
          list.add(code.getWage());
          list.add(code.getRegistArea());
          list.add(code.getResideTime());
          list.add(code.getPosition());
          list.add(code.getRentRate());
          list.add(code.getOverdue());
          list.add(code.getOverdueRate());
          isoff = false;
        } else {
          isoff = true;
          offmsg = code.getMsg();
        }
        return list;
      }

      @Override
      public Amount defaultScore() {
        return new Amount(0);
      }

      @Override
      public String getOffmsg() {
        return offmsg;
      }

      @Override
      public boolean getIsoff() {
        return isoff;
      }
    };

    double score =
        factorCustScoreService.insertFactorCustomer(customerinfo, factorCallBack);
    //返回信息ee
    ResultInfo resultInfo = new ResultInfo();
    resultInfo.setResultMsg(factorCallBack.getOffmsg());
    resultInfo.setResultCode(factorCallBack.getIsoff() ? "1" : "0");
    resultInfo.setCustomerName(customerinfo.getCustomername());
    resultInfo.setCertificateId(customerinfo.getCertificateid());
    resultInfo.setResultScore(String.valueOf(score));
    return resultInfo;
  }

  /**
   * customerMsg转换为Customerinfo
   */
  public static Customerinfo convertCustomerinfo(CustomerMsg customerMsg) {
    Customerinfo customerinfo = new Customerinfo();
    BeanMappper.fastCopy(customerMsg, customerinfo);
    /*customerinfo.setAge(intValueOf(customerMsg.getAge(), null));
    customerinfo.setResidetime(intValueOf(customerMsg.getResideTime(), null));
    customerinfo.setEntrytime(intValueOf(customerMsg.getEntryTime(), null));
    customerinfo.setWage(amountValueOf(customerMsg.getWage(), null));
    customerinfo.setHousesize(amountValueOf(customerMsg.getHouseSize(), null));
    customerinfo.setOtherestate(amountValueOf(customerMsg.getOtherEstate(), null));
    customerinfo.setRentrate(amountValueOf(customerMsg.getRentRate(), null));
    customerinfo.setBurdenrate(amountValueOf(customerMsg.getBurdenRate(), null));
    customerinfo.setCashdepositrate(amountValueOf(customerMsg.getCashDepositRate(), null));
    customerinfo.setFinancingtime(intValueOf(customerMsg.getFinancingTime(), null));
    customerinfo.setOverduerate(amountValueOf(customerMsg.getOverdueRate(), null));
    customerinfo.setOverdue(intValueOf(customerMsg.getOverdue(), null));
    customerinfo.setMaxoverdue(intValueOf(customerMsg.getMaxOverdue(), null));
    customerinfo.setGps(customerMsg.getGPS());*/
    return customerinfo;
  }

  /**
   * customerMsg转换为CustomerFactor
   */
  public CustomerFactor convertCustomerFactor(CustomerMsg customerMsg) {
    CustomerFactor customerFactor = new CustomerFactor();
    BeanMappper.fastCopy(customerMsg, customerFactor);
    customerFactor.setAge(intValueOf(customerMsg.getAge(), null));
    customerFactor.setResideTime(intValueOf(customerMsg.getResideTime(), null));
    customerFactor.setEntryTime(intValueOf(customerMsg.getEntryTime(), null));
    customerFactor.setWage(amountValueOf(customerMsg.getWage(), null));
    customerFactor.setHouseSize(amountValueOf(customerMsg.getHouseSize(), null));
    customerFactor.setOtherEstate(amountValueOf(customerMsg.getOtherEstate(), null));
    customerFactor.setRentRate(amountValueOf(customerMsg.getRentRate(), null));
    customerFactor.setBurdenRate(amountValueOf(customerMsg.getBurdenRate(), null));
    customerFactor.setCashDepositRate(amountValueOf(customerMsg.getCashDepositRate(), null));
    customerFactor.setFinancingTime(intValueOf(customerMsg.getFinancingTime(), null));
    customerFactor.setOverdueRate(amountValueOf(customerMsg.getOverdueRate(), null));
    customerFactor.setOverdue(intValueOf(customerMsg.getOverdue(), null));
    customerFactor.setMaxOverdue(intValueOf(customerMsg.getMaxOverdue(), null));

    return customerFactor;
  }
}
