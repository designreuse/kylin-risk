package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.WarningSet;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by 201508031790 on 2015/9/6.
 */
public interface WarningSetService {

  List<WarningSet> queryAll();

  void addWarningSet(WarningSet ws, String operatorIds);

  WarningSet queryById(String ws);

  WarningSet update(WarningSet ws);

  Boolean delWarnset(String ids);

  List<WarningSet> queryIsSendMails(LocalDate localDate, String warnType, String riskLevel);
}
