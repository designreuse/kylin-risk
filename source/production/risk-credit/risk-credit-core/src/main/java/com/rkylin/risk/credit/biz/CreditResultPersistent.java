package com.rkylin.risk.credit.biz;

import com.rkylin.risk.commons.entity.ExternalCreditResult;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import java.util.Map;

/**
 * Created by tomalloc on 16-8-2.
 */
public interface CreditResultPersistent {
  void persistent(ExternalCreditResult creditResult);

  ExternalCreditResult queryCommonCreditResult(String idNumber, CreditProductType creditProductType,
      String module);

  ExternalCreditResult queryUnionPayCreditResult(String bankNumber,
      CreditProductType creditProductType, String module);

  Map<CreditProductType, ExternalCreditResult> query(String id);
}
