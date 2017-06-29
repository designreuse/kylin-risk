package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.commons.entity.ExternalCreditResult;
import com.rkylin.risk.commons.entity.RiskCreditModule;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import com.rkylin.risk.core.dao.ExternalCreditResultDao;
import com.rkylin.risk.core.dao.RiskCreditModuleDao;
import com.rkylin.risk.core.service.ExternalCreditResultService;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by tomalloc on 16-8-23.
 */
@Service
public class ExternalCreditResultServiceImpl implements ExternalCreditResultService {
  @Resource
  private ExternalCreditResultDao externalCreditResultDao;

  @Resource
  private RiskCreditModuleDao riskCreditModuleDao;

  @Override public ExternalCreditResult insert(ExternalCreditResult creditResult, String module) {
    RiskCreditModule creditModule = riskCreditModuleDao.query(module, true);
    if (creditModule == null) {
      creditModule = new RiskCreditModule();
      creditModule.setAtomicQuery(true);
      creditModule.setModuleName(module);
      creditModule.setModuleQueryName(module);
      riskCreditModuleDao.insert(creditModule);
    }
    creditResult.setCreditModuleId(creditModule.getId());
    return externalCreditResultDao.insert(creditResult);
  }

  @Override public ExternalCreditResult insert(ExternalCreditResult creditResult, String[] module) {
    Objects.requireNonNull(module);
    String[] modules = module;
    Arrays.sort(modules);
    StringBuilder stringBuilder = new StringBuilder(modules[0]);
    int mLen = module.length;
    for (int i = 1; i < mLen; i++) {
      stringBuilder.append(",");
      stringBuilder.append(modules[i]);
    }
    String gatherModuleName = stringBuilder.toString();
    RiskCreditModule creditModule = riskCreditModuleDao.query(gatherModuleName, true);
    if (creditModule == null) {
      creditModule = new RiskCreditModule();
      creditModule.setAtomicQuery(true);
      creditModule.setModuleName(gatherModuleName);
      creditModule.setModuleQueryName(gatherModuleName);
      riskCreditModuleDao.insert(creditModule);
      if (mLen > 1) {
        for (String m : modules) {
          //RiskCreditModule childCreditModule=riskCreditModuleDao.query(m);

        }
      }
    }

    return externalCreditResultDao.insert(creditResult);
  }

  @Override
  public ExternalCreditResult queryCommonCreditResult(String idNumber,
      CreditProductType creditProductType,
      String module) {
    RiskCreditModule creditModule = riskCreditModuleDao.query(module, true);
    if (creditModule == null) {
      return null;
    }
    return externalCreditResultDao.queryCommonCreditResult(idNumber, creditProductType,
        creditModule.getId());
  }

  @Override public Set<ExternalCreditResult> queryCommonCreditResultCollection(String idNumber,
      CreditProductType creditProductType, List<String> moduleList) {
    return null;
  }

  @Override public ExternalCreditResult queryUnionPayCreditResult(String bankNumber,
      CreditProductType creditProductType, String module) {
    return externalCreditResultDao.queryUnionPayCreditResult(bankNumber, creditProductType, 0);
  }
}
