package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.commons.entity.ExternalCreditResult;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import com.rkylin.risk.core.dao.ExternalCreditResultDao;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by tomalloc on 16-8-23.
 */
@Repository
public class ExternalCreditResultDaoImpl extends BaseDaoImpl<ExternalCreditResult>
    implements ExternalCreditResultDao {

  @Override public ExternalCreditResult insert(ExternalCreditResult creditResult) {
    super.add(creditResult);
    return creditResult;
  }

  @Override
  public ExternalCreditResult queryCommonCreditResult(String idNumber,
      CreditProductType creditProductType,
      long module) {
    Map<String, Object> map = new HashMap<>();
    map.put("idNumber", idNumber);
    map.put("creditProduct", creditProductType);
    map.put("creditModuleId", module);
    map.put("riskCode", "10000");
    return super.queryOne("queryCommonCreditResult", map);
  }

  @Override public ExternalCreditResult queryUnionPayCreditResult(String bankCard,
      CreditProductType creditProductType, long module) {
    Map<String, Object> map = new HashMap<>();
    map.put("bankCard", bankCard);
    map.put("creditProduct", creditProductType);
    map.put("creditModule", module);
    return super.queryOne("queryUnionPayCreditResult", map);
  }
}
