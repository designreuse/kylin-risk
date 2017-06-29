package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.FactorTemplDao;
import com.rkylin.risk.core.entity.FactorTempl;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-5-9.
 */
@Repository("factorTemplDao")
public class FactorTemplDaoImpl extends BaseDaoImpl<FactorTempl> implements FactorTemplDao {

  @Override public FactorTempl findById(Short id) {
    return super.get(id);
  }

  @Override public FactorTempl addTempl(FactorTempl factorTempl) {
    super.add(factorTempl);
    return factorTempl;
  }

  @Override public void updateFactorTempl(FactorTempl factorTempl) {
    super.modify(factorTempl);
  }

  @Override public void delete(Short id) {
    super.del(id);
  }

  @Override public List<FactorTempl> queryByGroup(Short groupid) {
    return super.selectList("queryByGroup", groupid);
  }
}
