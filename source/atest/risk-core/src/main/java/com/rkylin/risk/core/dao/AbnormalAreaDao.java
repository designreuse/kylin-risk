package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.AbnormalArea;

import java.util.List;

/**
 * Created by 201508031790 on 2015/8/31.
 */
public interface AbnormalAreaDao {

  AbnormalArea addAbnormalArea(AbnormalArea entity);

  List<AbnormalArea> queryAll();

  AbnormalArea updateAbnormalArea(AbnormalArea entity);

  List<AbnormalArea> queryByCondition(AbnormalArea entity);

  AbnormalArea queryById(AbnormalArea entity);

  void insert(AbnormalArea abnormalArea);
}
