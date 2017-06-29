package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.CreditModule;
import java.util.List;

/**
 * Created by tomalloc on 16-9-29.
 */
public interface CreditModuleDao {
  CreditModule query(long id);

  List<CreditModule> query(String moduleQueryName);

  CreditModule query(String moduleName, boolean isAtomicQuery);

  /**
   * 添加模块
   */
  CreditModule insert(CreditModule creditModule);
}
