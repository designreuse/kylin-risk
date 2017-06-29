package com.rkylin.risk.service.biz.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.dto.ScoreRuleBean;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.service.FactorCallBack;
import com.rkylin.risk.core.service.FactorCustScoreService;
import com.rkylin.risk.core.utils.BeanMappper;
import com.rkylin.risk.kie.spring.factorybeans.KieContainerSession;
import com.rkylin.risk.operation.bean.CustomerMsg;
import com.rkylin.risk.operation.bean.ResultInfo;
import com.rkylin.risk.service.biz.CustomerCalBiz;
import java.util.List;
import javax.annotation.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;

/**
 * Created by lina on 2016-6-17.
 */
@Component("customerCalBiz")
public class CustomerCalBizImpl extends BaseBizImpl implements CustomerCalBiz {
  @Resource
  private KieContainerSession kieContainerSession;
  @Resource
  private FactorCustScoreService factorCustScoreService;

  @Override
  public ResultBean calCustomerFactor(ScoreRuleBean customer) {

    ResultBean score = new ResultBean();
    KieContainer kieContainer = kieContainerSession.getBean("");
    KieSession kieSession = kieContainer.newKieSession();
    kieSession.insert(customer);
    kieSession.insert(score);
    kieSession.fireAllRules();

    kieSession.destroy();
    return score;
  }

  @Override
  public ResultInfo customerInfoHandle(CustomerMsg customerMsg) {

    //customerMsg转换为Customerinfo
    Customerinfo customerinfo = CustomerBizImpl.convertCustomerinfo(customerMsg);
    ScoreRuleBean custBean = convertCustomerFactorBean(customerMsg);
    ResultBean scoreBean = null;

    ResultInfo result = new ResultInfo();
    scoreBean = calCustomerFactor(custBean);
    result.setResultCode(scoreBean.getIsOff());
    result.setResultScore(scoreBean.getScore() + "");
    result.setResultMsg(scoreBean.getOffMsg());

    FactorCallBack callBack = getCallBack(scoreBean);
    factorCustScoreService.insertFactorCustomer(
        customerinfo, scoreBean);
    //返回信息ee
    result.setCustomerName(customerinfo.getCustomername());
    result.setCertificateId(customerinfo.getCertificateid());
    return result;
  }

  public FactorCallBack getCallBack(final ResultBean scoreBean) {
    return new FactorCallBack() {
      @Override public List<String> doGetFactor() {
        List<String> list = Lists.newArrayList();
        if (scoreBean != null && !"1".equals(scoreBean.getIsOff())) {
          /*list.add(scoreBean.getAge());
          list.add(scoreBean.getBurdenRate());
          list.add(scoreBean.getCashDepositRate());
          list.add(scoreBean.getComType());
          list.add(scoreBean.getCustSource());
          list.add(scoreBean.getEduDegree());
          list.add(scoreBean.getEntryTime());
          list.add(scoreBean.getFinancingTime());
          list.add(scoreBean.getGps());
          list.add(scoreBean.getHouseArea());
          list.add(scoreBean.getHouseRegion());
          list.add(scoreBean.getHouseSize());
          list.add(scoreBean.getHouseType());
          list.add(scoreBean.getIllegalRecord());
          list.add(scoreBean.getLeftKey());
          list.add(scoreBean.getMarriage());
          list.add(scoreBean.getMaxOverdue());
          list.add(scoreBean.getMortInfo());
          list.add(scoreBean.getOtherEstate());
          list.add(scoreBean.getWage());
          list.add(scoreBean.getRegistArea());
          list.add(scoreBean.getResideTime());
          list.add(scoreBean.getPosition());
          list.add(scoreBean.getRentRate());
          list.add(scoreBean.getOverdue());
          list.add(scoreBean.getOverdueRate());*/
          return list;
        }
        return null;
      }

      @Override public Amount defaultScore() {
        return null;
      }

      @Override public String getOffmsg() {
        return null;
      }

      @Override public boolean getIsoff() {
        return false;
      }
    };
  }

  /**
   * customerMsg转换为CustomerFactorBean
   */
  public ScoreRuleBean convertCustomerFactorBean(CustomerMsg customerMsg) {
    ScoreRuleBean scoreRuleBean = new ScoreRuleBean();
    BeanMappper.fastCopy(customerMsg, scoreRuleBean);
    scoreRuleBean.setAge(amountValueOf(customerMsg.getAge(), null));
    scoreRuleBean.setResideTime(amountValueOf(customerMsg.getResideTime(), null));
    scoreRuleBean.setEntryTime(amountValueOf(customerMsg.getEntryTime(), null));
    scoreRuleBean.setWage(amountValueOf(customerMsg.getWage(), null));
    scoreRuleBean.setHouseSize(amountValueOf(customerMsg.getHouseSize(), null));
    scoreRuleBean.setOtherEstate(amountValueOf(customerMsg.getOtherEstate(), null));
    scoreRuleBean.setRentRate(amountValueOf(customerMsg.getRentRate(), null));
    scoreRuleBean.setBurdenRate(amountValueOf(customerMsg.getBurdenRate(), null));
    scoreRuleBean.setCashDepositRate(amountValueOf(
        customerMsg.getCashDepositRate(), null));
    scoreRuleBean.setFinancingTime(amountValueOf(customerMsg.getFinancingTime(), null));
    scoreRuleBean.setOverdueRate(amountValueOf(customerMsg.getOverdueRate(), null));
    scoreRuleBean.setOverdue(amountValueOf(customerMsg.getOverdue(), null));
    scoreRuleBean.setMaxOverdue(amountValueOf(customerMsg.getMaxOverdue(), null));
    scoreRuleBean.setGps(customerMsg.getGPS());
    return scoreRuleBean;
  }
}
