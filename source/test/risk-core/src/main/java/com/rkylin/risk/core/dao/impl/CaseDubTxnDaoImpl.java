package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CaseDubTxnDao;
import com.rkylin.risk.core.entity.CaseDubTxn;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by 201507270241 on 2015/9/23.
 */
@Repository
public class CaseDubTxnDaoImpl extends BaseDaoImpl<CaseDubTxn> implements CaseDubTxnDao {
  @Override
  public List<CaseDubTxn> queryByCaseId(Integer caseid) {
    return super.selectList("queryByCaseId", caseid);
  }

  @Override
  public void insert(CaseDubTxn caseDubTxn) {
    super.add(caseDubTxn);
  }
}
