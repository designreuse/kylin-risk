package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.AbnorIndustryDao;
import com.rkylin.risk.core.entity.AbnorIndustry;
import com.rkylin.risk.core.service.AbnorIndustryService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201507270241 on 2015/8/28.
 */
@Service("abnorIndustryService")
public class AbnorIndustryServiceImpl implements AbnorIndustryService {

  @Resource
  private AbnorIndustryDao abnorIndustryDao;

  @Override
  public AbnorIndustry queryOne(Integer id) {
    return abnorIndustryDao.queryOne(id);
  }

  @Override
  public List<AbnorIndustry> queryAll(AbnorIndustry abnorIndustry) {
    return abnorIndustryDao.queryAll(abnorIndustry);
  }

  @Override
  public void insert(List<AbnorIndustry> abnorIndustryList) {
    for (AbnorIndustry abnorIndustry : abnorIndustryList) {
      abnorIndustryDao.insert(abnorIndustry);
    }
  }

  @Override
  public void delete(Integer id) {
    abnorIndustryDao.delete(id);
  }

  @Override
  public void update(AbnorIndustry abnorIndustry) {
    abnorIndustryDao.update(abnorIndustry);
  }
}
