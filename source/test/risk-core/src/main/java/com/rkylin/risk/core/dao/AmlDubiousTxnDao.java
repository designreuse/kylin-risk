package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.AmlDubiousTxn;

import java.util.List;
import java.util.Map;

/**
 * Created by 201507270241 on 2015/9/17.
 */
public interface AmlDubiousTxnDao {

  List<AmlDubiousTxn> queryAll(AmlDubiousTxn amlDubiousTxn);

  AmlDubiousTxn queryOne(Integer id);

  void insert(AmlDubiousTxn amlDubiousTxn);

  int update(AmlDubiousTxn amlDubiousTxn);

  void delete(Integer id);

  List querybycondition(Map map);
}
