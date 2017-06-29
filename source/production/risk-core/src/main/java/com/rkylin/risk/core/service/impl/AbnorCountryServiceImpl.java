package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.AbnorCountryDao;
import com.rkylin.risk.core.entity.AbnormalCountrycode;
import com.rkylin.risk.core.service.AbnorCountryService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201507270241 on 2015/8/31.
 */
@Service("abnorCountryService")
public class AbnorCountryServiceImpl implements AbnorCountryService {
  @Resource
  private AbnorCountryDao abnorCountryDao;

  @Override
  public AbnormalCountrycode queryOne(Integer id) {
    return abnorCountryDao.queryOne(id);
  }

  @Override
  public List<AbnormalCountrycode> queryAll(AbnormalCountrycode abnormalCountrycode) {
    return abnorCountryDao.queryAll(abnormalCountrycode);
  }

  @Override
  public void insert(List<AbnormalCountrycode> abnormalCountrycodeList) {
    for (AbnormalCountrycode abnormalCountrycode : abnormalCountrycodeList) {
      abnorCountryDao.insert(abnormalCountrycode);
    }
  }

  @Override
  public void delete(Integer id) {
    abnorCountryDao.delete(id);
  }

  @Override
  public void update(AbnormalCountrycode abnormalCountrycode) {
    abnorCountryDao.update(abnormalCountrycode);
  }
}