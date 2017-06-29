package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.AbnormalArea;

import java.util.List;

/**
 * Created by 201508031790 on 2015/8/31.
 */
public interface AbnormalAreaService {

  AbnormalArea addAbnormalArea(AbnormalArea entity);

  List<AbnormalArea> queryAll();

  AbnormalArea updateAbnormalArea(AbnormalArea code);

  List<AbnormalArea> queryByCondition(AbnormalArea code);

  AbnormalArea queryById(AbnormalArea area);

  void insert(List<AbnormalArea> abnormalAreaList);
}
