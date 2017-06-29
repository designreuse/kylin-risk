package com.rkylin.risk.core.service.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.CustomerinfoDao;
import com.rkylin.risk.core.dao.FactorCustRelationDao;
import com.rkylin.risk.core.dao.FactorDao;
import com.rkylin.risk.core.dao.RiskGradeCustDao;
import com.rkylin.risk.core.dao.ScoreLevelDao;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.dto.RuleBean;
import com.rkylin.risk.core.dto.ScoreRuleBean;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.Factor;
import com.rkylin.risk.core.entity.FactorCustRelation;
import com.rkylin.risk.core.entity.RiskGradeCust;
import com.rkylin.risk.core.entity.ScoreLevel;
import com.rkylin.risk.core.exception.RiskException;
import com.rkylin.risk.core.service.FactorCallBack;
import com.rkylin.risk.core.service.FactorCustScoreService;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by lina on 2016-4-5.
 */
@Service
public class FactorCustScoreServiceImpl implements FactorCustScoreService {

  @Resource
  private FactorDao factorDao;
  @Resource
  private ScoreLevelDao scoreLevelDao;
  @Resource
  private CustomerinfoDao customerinfoDao;
  @Resource
  private FactorCustRelationDao factorCustRelationDao;
  @Resource
  private RiskGradeCustDao riskGradeCustDao;

  @Override
  public double insertFactorCustomer(Customerinfo customerinfo, FactorCallBack callBack) {
    return insertFactorCustomer(customerinfo, callBack, null);
  }

  @Override
  public void insertFactorCustomer(Customerinfo customerinfo, ResultBean scoreBean) {
    insertFactorCustomer(customerinfo, null, scoreBean);
  }

  /**
   * 客户评分业务处理:插入客户信息，客户风险等级信息，以及单项因子分数信息
   */

  public double insertFactorCustomer(Customerinfo customerinfo, FactorCallBack callBack,
      ResultBean scoreBean) {
    String score = scoreBean == null ? "0" : Double.toString(scoreBean.getScore());
    //插入客户信息
    customerinfo.setScore(new Amount(score));
    Customerinfo customer = customerinfoDao.insert(customerinfo);
    if (scoreBean == null && callBack != null) {
      //批量插入单项因子分数
      score = Double.toString(insertBatchScore(customer.getId(), callBack));
    } else {
      try {
        insertBatchFactor(customer.getId(), scoreBean);
      } catch (Exception e) {
        throw new RiskException(e);
      }
    }

    //插入客户风险等级
    ScoreLevel scoreLevel = scoreLevelDao.getLevel(new Double(score));
    RiskGradeCust riskGradeCust = new RiskGradeCust();
    riskGradeCust.setCustomerid(customer.getCustomerid());
    riskGradeCust.setCustid(customer.getId());
    riskGradeCust.setCustomername(customer.getCustomername());
    riskGradeCust.setTotalscore(score);
    riskGradeCust.setGrade(scoreLevel.getRisklevel());
    riskGradeCust.setStatus(Constants.ACTIVE);
    //风控规则
    riskGradeCust.setRisktype("00");
    riskGradeCustDao.create(riskGradeCust);

    return new Double(score);
  }

  /**
   * 客户评分单项因子分数批量添加数据
   *
   * @return 评分
   */
  private void insertBatchFactor(Long custid, ResultBean scoreBean)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Field[] fields = ScoreRuleBean.class.getDeclaredFields();
    List<FactorCustRelation> list = Lists.newArrayList();

    for (Field field : fields) {
      String fieldName = field.getName();
      if (!"score".equals(fieldName)) {
        FactorCustRelation factorCustRelation = new FactorCustRelation();
        factorCustRelation.setCustid(custid);
        factorCustRelation.setFactorcode(fieldName);
        Method getMethod = ResultBean.class.getDeclaredMethod(
            "get" + RuleBean.firstLetterToUpper(fieldName));
        factorCustRelation.setFactorscore((String) getMethod.invoke(scoreBean));
        list.add(factorCustRelation);
      }
    }
    factorCustRelationDao.insertBatch(list);
  }

  /**
   * 客户评分单项因子分数批量添加数据并且累加计算分值
   *
   * @return 评分
   */
  private double insertBatchScore(Long custid, FactorCallBack callBack) {
    BigDecimal amount = new BigDecimal("00");
    List<String> codeLs = callBack.doGetFactor();
    if (codeLs != null) {
      List<FactorCustRelation> factorCustRelationList = Lists.newArrayList();
      for (String code : codeLs) {
        if (code != null) {
          Factor factor = factorDao.findByCode(code);
          if (factor != null) {
            FactorCustRelation factorCustRelation = new FactorCustRelation();
            factorCustRelation.setCustid(custid);
            factorCustRelation.setFactorid(factor.getId());
            factorCustRelation.setFactorscore(factor.getAccount());
            amount = amount.add(new BigDecimal(factor.getAccount()));
            factorCustRelationList.add(factorCustRelation);
          }
        }
      }
      factorCustRelationDao.insertBatch(factorCustRelationList);
    }
    return amount.doubleValue();
  }
}
