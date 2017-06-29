package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Case;

import java.util.List;
import java.util.Map;

/**
 * Created by 201507270241 on 2015/9/23.
 */
public interface CaseDao {

  List<Case> queryAll(Case cases);

  Case queryOne(Integer id);

  void insert(Case cases);

  List<Case> exportCaseExcel(Map map);
}
