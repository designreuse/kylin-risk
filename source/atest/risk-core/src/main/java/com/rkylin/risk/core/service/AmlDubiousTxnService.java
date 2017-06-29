package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.AmlDubiousTxn;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Created by 201507270241 on 2015/9/17.
 */
public interface AmlDubiousTxnService {

  List<AmlDubiousTxn> queryAll(AmlDubiousTxn amlDubiousTxn);

  AmlDubiousTxn queryOne(Integer id);

  void add(AmlDubiousTxn amlDubiousTxn);

  void modify(AmlDubiousTxn amlDubiousTxn);

  void delete(Integer id);

  List<Map> queryByCondition(AmlDubiousTxn adt, LocalDate begin, LocalDate end);
}
