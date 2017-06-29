package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CreditResultDao;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.enumtype.CreditProductType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by tomalloc on 16-8-23.
 */
@Repository
public class CreditResultDaoImpl extends BaseDaoImpl<CreditResult>
    implements CreditResultDao {

  public void insertBatch(List<CreditResult> creditResultList) {
    super.addBatch("insertBatch", creditResultList);
  }

  @Override public CreditResult insert(CreditResult creditResult) {
    super.add(creditResult);
    return creditResult;
  }

  @Override
  public CreditResult queryCommonCreditResult(String idNumber,
      CreditProductType creditProductType,
      Long module) {
    Map<String, Object> map = new HashMap<>();
    map.put("idNumber", idNumber);
    map.put("creditProduct", creditProductType);
    map.put("creditModuleId", module);
    return super.selectOne("queryCommonCreditResult", map);
  }

  @Override public List<CreditResult> queryUnionPayCreditResult(String bankCard) {
    return super.selectList("queryUnionPayCreditResult", bankCard);
  }

  @Override
  public List<CreditResult> queryBairongCreditResult(String name, String idNumber,
      String mobile,
      long module) {
    Map<String, Object> map = new HashMap<>();
    map.put("userName", name);
    map.put("idNumber", idNumber);
    map.put("mobile", mobile);
    map.put("creditModuleId", module);
    return super.selectList("queryBairongCreditResult", map);
  }
}
