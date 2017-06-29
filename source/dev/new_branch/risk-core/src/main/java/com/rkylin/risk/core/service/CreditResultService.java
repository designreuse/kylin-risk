package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.CreditModule;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.enumtype.CreditProductType;
import java.util.List;

/**
 * Created by tomalloc on 16-8-23.
 */
public interface CreditResultService {

  CreditResult insert(CreditResult creditResult, String[] module);

  void insert(CreditResult creditResult);

  void insert(List<CreditResult> creditResult);

  CreditModule fetchCreditModue(String module);

  CreditModule fetchCreditModue(String[] moduleList);

  CreditResult queryCommonCreditResult(String idNumber,
      CreditProductType creditProductType,
      String module);

  /**
   * 银联智策查询
   *
   * @param bankCard 银行卡号
   */
  List<CreditResult> queryUnionPayCreditResult(String bankCard);

  List<CreditResult> queryBairongCreditResult(String userName, String mobile, String idNumber,
      String[] module);
}
