package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.CaseDubTxn;
import java.util.List;

/**
 * Created by 201507270241 on 2015/9/23.
 */
public interface CaseDubTxnDao {

  List<CaseDubTxn> queryByCaseId(Integer caseid);

  void insert(CaseDubTxn caseDubTxn);
}
