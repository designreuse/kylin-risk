package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.FactorLibraryDao;
import com.rkylin.risk.core.dao.FactorTemplDao;
import com.rkylin.risk.core.entity.FactorLibrary;
import com.rkylin.risk.core.entity.FactorTempl;
import com.rkylin.risk.core.service.FactorTemplService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by lina on 2016-5-23.
 */
@Service("factorTemplService")
public class FactorTemplServiceImpl implements FactorTemplService {
  @Resource
  private FactorTemplDao factorTemplDao;
  @Resource
  private FactorLibraryDao factorLibraryDao;

  @Override public void add(String groupid, String libraryid) {
    if (!StringUtils.isEmpty(libraryid)) {
      String[] libid = libraryid.split(",");
      for (String id : libid) {
        FactorTempl templ = new FactorTempl();
        FactorLibrary library = factorLibraryDao.queryById(Short.parseShort(id));
        templ.setGroupid(Short.parseShort(groupid));
        templ.setCode(library.getCode());
        templ.setName(library.getName());
        factorTemplDao.addTempl(templ);
      }
    }
  }

  @Override public void updateFactorTempl(FactorTempl factorTempl) {
    factorTemplDao.updateFactorTempl(factorTempl);
  }

  @Override public FactorTempl findById(Short id) {
    return factorTemplDao.findById(id);
  }

  @Override public void deleteFactorTempl(String ids) {
    if (!StringUtils.isEmpty(ids)) {
      String[] idArr = ids.split(",");
      for (String id : idArr) {
        factorTemplDao.delete(Short.parseShort(id));
      }
    }
  }

  @Override public List<FactorTempl> queryByGroup(Short groupid) {
    return factorTemplDao.queryByGroup(groupid);
  }
}
