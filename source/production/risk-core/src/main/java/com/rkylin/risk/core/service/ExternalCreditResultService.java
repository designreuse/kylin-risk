package com.rkylin.risk.core.service;

import com.rkylin.risk.commons.entity.ExternalCreditResult;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import java.util.List;
import java.util.Set;

/**
 * Created by tomalloc on 16-8-23.
 */
public interface ExternalCreditResultService {

  ExternalCreditResult insert(ExternalCreditResult creditResult, String module);

  ExternalCreditResult insert(ExternalCreditResult creditResult, String[] module);

  ExternalCreditResult queryCommonCreditResult(String idNumber, CreditProductType creditProductType,
      String moduleList);

  Set<ExternalCreditResult> queryCommonCreditResultCollection(String idNumber,
      CreditProductType creditProductType,
      List<String> moduleList);

  ExternalCreditResult queryUnionPayCreditResult(String bankNumber,
      CreditProductType creditProductType,
      String module);
}
