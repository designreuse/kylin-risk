package com.rkylin.risk.credit.biz.impl;

import com.rkylin.risk.commons.entity.ExternalCreditResult;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import com.rkylin.risk.core.service.ExternalCreditResultService;
import com.rkylin.risk.credit.biz.CreditResultPersistent;
import java.util.Map;
import javax.annotation.Resource;

/**
 * Created by tomalloc on 16-8-23.
 */
public class ExternalCreditResultBizImpl implements CreditResultPersistent {
  @Resource
  private ExternalCreditResultService externalCreditResultService;

  @Override public void persistent(ExternalCreditResult creditResult) {
    externalCreditResultService.insert(creditResult);
  }

  @Override public ExternalCreditResult queryCommonCreditResult(String idNumber,
      CreditProductType creditProductType,
      String module) {
    return externalCreditResultService.queryCommonCreditResult(idNumber, creditProductType, module);
  }

  @Override public ExternalCreditResult queryUnionPayCreditResult(String bankNumber,
      CreditProductType creditProductType, String module) {
    return externalCreditResultService.queryUnionPayCreditResult(bankNumber, creditProductType,
        module);
  }

  @Override public Map<CreditProductType, ExternalCreditResult> query(String id) {
    return null;
  }
}
