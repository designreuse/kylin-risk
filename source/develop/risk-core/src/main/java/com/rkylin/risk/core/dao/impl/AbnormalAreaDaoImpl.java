package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.AbnormalAreaDao;
import com.rkylin.risk.core.entity.AbnormalArea;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508031790 on 2015/8/31.
 */
@Repository("abnormalAreaDao")
public class AbnormalAreaDaoImpl extends BaseDaoImpl<AbnormalArea> implements AbnormalAreaDao {

  @Override
  public AbnormalArea addAbnormalArea(AbnormalArea entity) {
    super.add(entity);
    return entity;
  }

  @Override
  public AbnormalArea updateAbnormalArea(AbnormalArea entity) {
    super.modify(entity);
    return entity;
  }

  @Override
  public List<AbnormalArea> queryByCondition(AbnormalArea entity) {
    return super.query("queryAbnormalAreaByCondition", entity);
  }

  @Override
  public List<AbnormalArea> queryAll() {
    return super.queryAllList();
  }

  @Override
  public AbnormalArea queryById(AbnormalArea entity) {
    return super.get(entity);
  }

  @Override
  public void insert(AbnormalArea abnormalArea) {
    super.add(abnormalArea);
  }
}
