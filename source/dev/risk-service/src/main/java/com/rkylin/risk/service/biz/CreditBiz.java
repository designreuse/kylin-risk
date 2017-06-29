package com.rkylin.risk.service.biz;

import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.service.bean.CreditParam;
import com.rkylin.risk.service.bean.CreditRequestEntity;
import com.rkylin.risk.service.credit.CreditRequester;
import java.util.List;

/**
 * Created by tomalloc on 16-12-12.
 */
public interface CreditBiz {


  List<CreditResult> queryCreditResult(CreditParam param,CreditRequester creditRequester,String[] module);
  List<CreditResult> queryCreditResult(CreditRequestEntity requestEntity,CreditRequester creditRequester,String[] module);

}
