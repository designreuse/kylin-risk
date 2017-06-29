package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CreditModuleDao;
import com.rkylin.risk.core.dao.CreditResultDao;
import com.rkylin.risk.core.entity.CreditModule;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.enumtype.CreditProductType;
import com.rkylin.risk.core.service.CreditResultService;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by tomalloc on 16-8-23.
 */
@Service
public class CreditResultServiceImpl implements CreditResultService {
  @Resource
  private CreditResultDao creditResultDao;

  @Resource
  private CreditModuleDao creditModuleDao;

  @Override public CreditResult insert(CreditResult creditResult, String[] module) {
    CreditModule creditModule = fetchCreditModue(module);
    creditResult.setCreditModuleId(creditModule.getId());
    return creditResultDao.insert(creditResult);
  }

  @Override public void insert(CreditResult creditResult) {
    creditResultDao.insert(creditResult);
  }

  @Override public void insert(List<CreditResult> creditResultList) {
    creditResultDao.insertBatch(creditResultList);
  }

  public CreditModule fetchCreditModue(String[] module) {
    Objects.requireNonNull(module);
    Arrays.sort(module);
    return fetchCreditModue(StringUtils.join(module, ","));
  }

  @Override public CreditModule fetchCreditModue(String module) {
    CreditModule creditModule = creditModuleDao.query(module, true);
    if (creditModule == null) {
      creditModule = new CreditModule();
      creditModule.setAtomicQuery(true);
      creditModule.setModuleName(module);
      creditModule.setModuleQueryName(module);
      creditModuleDao.insert(creditModule);
    }
    return creditModule;
  }

  //TODO
  @Override
  public CreditResult queryCommonCreditResult(String idNumber,
      CreditProductType creditProductType,
      String module) {
    CreditModule creditModule = creditModuleDao.query(module, true);
    if (creditModule == null) {
      return null;
    }
    return creditResultDao.queryCommonCreditResult(idNumber, creditProductType,
        creditModule.getId());
  }

  @Override public List<CreditResult> queryUnionPayCreditResult(String bankCard) {
    return creditResultDao.queryUnionPayCreditResult(bankCard);
  }

  @Override public List<CreditResult> queryBairongCreditResult(String userName, String mobile,
      String idNumber, String[] module) {
    CreditModule creditModule = creditModuleDao.query(StringUtils.join(module, ","), true);
    if (creditModule == null) {
      return null;
    }
    return creditResultDao.queryBairongCreditResult(userName, idNumber, mobile,
        creditModule.getId());
  }
}
