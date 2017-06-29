package com.rkylin.risk.service.credit;

import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.service.bean.CreditRequestEntity;
import java.util.List;

/**
 * Created by tomalloc on 16-12-13.
 */
public interface CreditRequester {

  long fetchCreditModue(String[] module);

  List<CreditResult> queryCreditResult(CreditRequestEntity requestEntity, String[] module);

  CreditResult requestCreditResult(CreditRequestEntity requestEntity, String[] module);
}
