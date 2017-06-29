package com.rkylin.risk.service.biz.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.service.CreditResultService;
import com.rkylin.risk.service.bean.CreditParam;
import com.rkylin.risk.service.bean.CreditRequestEntity;
import com.rkylin.risk.service.biz.CreditBiz;
import com.rkylin.risk.service.credit.CreditRequester;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by tomalloc on 16-12-12.
 */
@Slf4j
@Component("creditBiz")
public class CreditBizImpl implements CreditBiz {

  @Resource
  private CreditResultService creditResultService;

  @Override
  public List<CreditResult> queryCreditResult(CreditParam param, CreditRequester creditRequester,
      String[] module) {
    List<CreditRequestEntity> creditRequestEntityList = param.getData();
    String queries = param.getQuerier();

    long moduleId = creditRequester.fetchCreditModue(module);

    int querySize = creditRequestEntityList.size();
    if (param.isQueryAgain()) {
      //如果需要重新查询
      List<CreditResult> creditResultListDB = new ArrayList<>(5);
      List<CreditResult> creditResultList = new ArrayList<>(querySize);
      for (CreditRequestEntity requestEntity : creditRequestEntityList) {
        CreditResult creditResult = creditRequester.requestCreditResult(requestEntity, module);
        creditResult.setQuerier(queries);
        creditResult.setCreditModuleId(moduleId);

        creditResultList.add(creditResult);
        creditResultListDB.add(creditResult);
        if (creditResultListDB.size() == 5) {
          creditResultService.insert(creditResultListDB);
          creditResultListDB.clear();
        }
      }
      if (!creditResultListDB.isEmpty()) {
        creditResultService.insert(creditResultListDB);
        creditResultListDB.clear();
        creditResultListDB = null;
      }

      return creditResultList;
    } else {
      List<CreditResult> creditResultList = new ArrayList<>(querySize + querySize >> 1);
      List<CreditResult> creditResultListDB = new ArrayList<>(5);
      for (CreditRequestEntity requestEntity : creditRequestEntityList) {
        List<CreditResult> creditResultDB =
            creditRequester.queryCreditResult(requestEntity, module);
        if (creditResultDB==null||creditResultDB.isEmpty()) {
          CreditResult creditResult = creditRequester.requestCreditResult(requestEntity, module);
          creditResult.setQuerier(queries);
          creditResult.setCreditModuleId(moduleId);

          creditResultList.add(creditResult);

          creditResultListDB.add(creditResult);
          if (creditResultListDB.size() == 5) {
            creditResultService.insert(creditResultListDB);
            creditResultListDB.clear();
          }
        } else {
          creditResultList.addAll(creditResultDB);
        }
      }
      if (!creditResultListDB.isEmpty()) {
        creditResultService.insert(creditResultListDB);
        creditResultListDB.clear();
        creditResultListDB = null;
      }
      return creditResultList;
    }
  }

  @Override public List<CreditResult> queryCreditResult(CreditRequestEntity requestEntity,
      CreditRequester creditRequester, String[] module) {
    List<CreditResult> creditResultDB = creditRequester.queryCreditResult(requestEntity, module);
    if (creditResultDB!=null&&!creditResultDB.isEmpty()) {
      return creditResultDB;
    }
    long moduleId = creditRequester.fetchCreditModue(module);
    final CreditResult creditResult = creditRequester.requestCreditResult(requestEntity, module);
    creditResult.setCreditModuleId(moduleId);
    creditResultService.insert(creditResult);
    return Lists.newArrayList(creditResult);
  }
}
