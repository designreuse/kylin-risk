package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.FactorLibraryDao;
import com.rkylin.risk.core.entity.FactorLibrary;
import com.rkylin.risk.core.service.FactorLibraryService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by lina on 2016-7-28.
 */
@Service
public class FactorLibraryServiceImpl implements FactorLibraryService {
  @Resource
  private FactorLibraryDao factorLibraryDao;
  @Override public FactorLibrary queryById(Short id) {
    return factorLibraryDao.queryById(id);
  }

  @Override public FactorLibrary addFactoryLibrary(FactorLibrary factorLibrary) {
    return factorLibraryDao.addFactoryLibrary(factorLibrary);
  }

  @Override public void update(FactorLibrary factorLibrary) {
    factorLibraryDao.update(factorLibrary);
  }

  @Override public List<FactorLibrary> queryByType(String type) {
    return factorLibraryDao.queryByType(type);
  }

  @Override public void deleteById(Short id) {
    factorLibraryDao.deleteById(id);
  }

  @Override public void updateFactorStatus(String ids, String status) {
    if (ids != null) {
      String[] idArr = ids.split(",");
      for (String id : idArr) {
        FactorLibrary factorLibrary = new FactorLibrary();
        factorLibrary.setId(Short.parseShort(id));
        factorLibrary.setStatus("true".equals(status) ? Constants.ACTIVE : Constants.INACTIVE);
        factorLibraryDao.update(factorLibrary);
      }
    }
  }
}
