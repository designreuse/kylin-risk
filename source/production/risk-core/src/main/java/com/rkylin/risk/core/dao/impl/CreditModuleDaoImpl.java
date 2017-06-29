package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CreditModuleDao;
import com.rkylin.risk.core.entity.CreditModule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by tomalloc on 16-9-29.
 */
@Repository("riskCreditModuleDao")
public class CreditModuleDaoImpl extends BaseDaoImpl<CreditModule>
    implements CreditModuleDao {
  @Override public CreditModule query(long id) {
    return super.get(id);
  }

  @Override public List<CreditModule> query(String moduleQueryName) {
    return super.selectList("queryByModuleQueryName", moduleQueryName);
  }

  @Override public CreditModule query(String moduleName, boolean isAtomicQuery) {
    Map map = new HashMap();
    map.put("moduleName", moduleName);
    map.put("isAtomicQuery", isAtomicQuery);
    return super.selectOne("queryByModulename", map);
  }

  @Override public CreditModule insert(CreditModule creditModule) {
    super.add(creditModule);
    return creditModule;
  }
}
