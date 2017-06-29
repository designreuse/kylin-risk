package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.ReptileDao;
import com.rkylin.risk.core.entity.Reptile;
import com.rkylin.risk.core.service.ReptileService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by lina on 2016-8-5.
 */
@Service
public class ReptileServiceImpl implements ReptileService {
  @Resource
  private ReptileDao reptileDao;

  @Override public Reptile insert(Reptile reptile) {
    return reptileDao.insert(reptile);
  }

  @Override public Reptile queryByCheckorderid(String checkorderid, String queryType) {
    return reptileDao.queryByCheckorderid(checkorderid, queryType);
  }

  @Override public void update(Reptile reptile) {
    reptileDao.update(reptile);
  }
}
