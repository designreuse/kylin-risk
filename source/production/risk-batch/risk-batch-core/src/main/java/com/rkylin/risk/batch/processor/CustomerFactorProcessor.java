package com.rkylin.risk.batch.processor;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.Factor;
import com.rkylin.risk.core.entity.FactorCustRelation;
import com.rkylin.risk.core.entity.RiskGradeCust;
import com.rkylin.risk.core.exception.RiskException;
import com.rkylin.risk.core.service.FactorCustRelationService;
import com.rkylin.risk.core.service.FactorService;
import com.rkylin.risk.core.service.RiskGradeCustService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by 201508240185 on 2015/10/26.
 */
@Slf4j
public class CustomerFactorProcessor implements ItemProcessor<Customerinfo, RiskGradeCust> {
  @Resource
  private FactorService factorService;
  @Resource
  private RiskGradeCustService riskGradeCustService;
  @Resource
  private FactorCustRelationService factorCustRelationService;

  @Override
  public RiskGradeCust process(Customerinfo customer) throws Exception {
    RiskGradeCust grade = riskGradeCustService.queryByCustRisk(customer.getId(), "00");
    try {
      //CustomerCode code = getCustFactor(customer);
      Amount score = new Amount(0);
      if (grade != null) {
        //已存在客户总分,删除客户因子关系表
        factorCustRelationService.deleteByCust(customer.getId());
      }
      //获取因子分数
      // score  = computedScore(code.getAge(),customer.getId(),score);

      if (grade == null) {
        grade = new RiskGradeCust();
        grade.setCreatetime(DateTime.now());
        grade.setCustid(customer.getId());
        grade.setCustomerid(customer.getCustomerid());
        grade.setCustomername(customer.getCustomername());
        grade.setRisktype("00");
        grade.setStatus("00");
        grade.setTotalscore(score + "");
      } else {
        grade.setCreatetime(DateTime.now());
        grade.setTotalscore(score + "");
      }
      log.info("客户" + customer.getCustomerid() + ",计算分数成功");
    } catch (Exception e) {
      log.info("客户" + customer.getCustomerid() + ",计算分数失败");
      throw e;
    }
    return grade;
  }

  private Amount computedScore(String code, Long custid, Amount score) {
    Amount scoreTemp = score;
    if (code != null) {
      Factor factor = factorService.findByCode(code);
      if (factor != null) {
        FactorCustRelation relation = new FactorCustRelation();
        relation.setCustid(custid);
        relation.setFactorid(factor.getId());
        relation.setFactorscore(factor.getAccount());
        factorCustRelationService.insert(relation);
        scoreTemp = score.add(new Amount(factor.getAccount()));
      } else {
        throw new RiskException(code + "因子不存在！");
      }
    }
    return scoreTemp;
  }
}
