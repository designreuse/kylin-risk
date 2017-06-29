package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.AbnormalAreaDao;
import com.rkylin.risk.core.entity.AbnormalArea;
import com.rkylin.risk.core.service.AbnormalAreaService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by 201508031790 on 2015/8/31.
 */
@Service("abnormalAreaService")
public class AbnormalAreaServiceImpl implements AbnormalAreaService {

  @Resource AbnormalAreaDao abnormalAreaDao;

  @Override
  public List<AbnormalArea> queryByCondition(AbnormalArea entity) {
    return abnormalAreaDao.queryByCondition(entity);
  }

  @Override
  public AbnormalArea updateAbnormalArea(AbnormalArea entity) {
    return abnormalAreaDao.updateAbnormalArea(entity);
  }

  @Override
  public List<AbnormalArea> queryAll() {
    return abnormalAreaDao.queryAll();
  }

  @Override
  public AbnormalArea addAbnormalArea(AbnormalArea entity) {
    return abnormalAreaDao.addAbnormalArea(entity);
  }

  @Override
  public AbnormalArea queryById(AbnormalArea area) {
    return abnormalAreaDao.queryById(area);
  }

  @Override
  public void insert(List<AbnormalArea> abnormalAreaList) {
    for (AbnormalArea abnormalArea : abnormalAreaList) {
      abnormalAreaDao.insert(abnormalArea);
    }
  }
}
