package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.enumtype.CreditProductType;
import java.util.List;

/**
 * Created by tomalloc on 16-8-23.
 */
public interface CreditResultDao {

  void insertBatch(List<CreditResult> creditResultList);

  /**
   * 添加对外征信数据
   */
  CreditResult insert(CreditResult creditResult);

  CreditResult queryCommonCreditResult(String idNumber, CreditProductType creditProductType,
      Long module);

  List<CreditResult> queryUnionPayCreditResult(String bankNumber);

  List<CreditResult> queryBairongCreditResult(String name, String idNumber, String mobile,
      long module);
}
