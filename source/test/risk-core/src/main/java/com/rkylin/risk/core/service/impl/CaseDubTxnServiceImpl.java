package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CaseDubTxnDao;
import com.rkylin.risk.core.entity.CaseDubTxn;
import com.rkylin.risk.core.service.CaseDubTxnService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201507270241 on 2015/9/23.
 */
@Service
public class CaseDubTxnServiceImpl implements CaseDubTxnService {
  @Resource
  private CaseDubTxnDao caseDubTxnDao;

  @Override
  public List<CaseDubTxn> queryByCaseId(Integer caseid) {
    return caseDubTxnDao.queryByCaseId(caseid);
  }

  @Override
  public void insert(CaseDubTxn caseDubTxn) {
    caseDubTxnDao.insert(caseDubTxn);
  }
}
