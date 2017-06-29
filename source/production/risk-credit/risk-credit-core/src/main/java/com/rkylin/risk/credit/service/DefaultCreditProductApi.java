package com.rkylin.risk.credit.service;

import com.rkylin.risk.commons.entity.ExternalCreditResult;

/**
 * Created by tomalloc on 16-9-1.
 */
public abstract class DefaultCreditProductApi<T> extends AbstractCreditProductApi<T> {
  @Override protected ExternalCreditResult queryFromDataBase(CreditRequestParam requestParam) {
    return getCreditResultPersistent().queryCommonCreditResult(requestParam.getIdNumber(),
        productType(), module());
  }
}
