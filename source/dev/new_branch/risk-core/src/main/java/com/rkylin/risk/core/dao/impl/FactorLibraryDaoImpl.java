package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.FactorLibraryDao;
import com.rkylin.risk.core.entity.FactorLibrary;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-7-28.
 */
@Repository
public class FactorLibraryDaoImpl extends BaseDaoImpl<FactorLibrary> implements FactorLibraryDao {
  @Override public FactorLibrary queryById(Short id) {
    return super.get(id);
  }

  @Override public FactorLibrary addFactoryLibrary(FactorLibrary factorLibrary) {
    super.add(factorLibrary);
    return factorLibrary;
  }

  @Override public void update(FactorLibrary factorLibrary) {
    super.modify(factorLibrary);
  }

  @Override public List<FactorLibrary> queryByType(String type) {
    return super.selectList("queryByType", type);
  }

  @Override public void deleteById(Short id) {
    super.del(id);
  }

  @Override
  public int checkExistCode(FactorLibrary factorLibrary) {
    return super.selectList("checkExistCode", factorLibrary).size();
  }
}
