package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.WarningSet;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by 201508031790 on 2015/9/6.
 */
public interface WarningSetDao {

  List<WarningSet> queryAll();

  WarningSet insert(WarningSet ws);

  WarningSet queryById(String ws);

  WarningSet update(WarningSet ws);

  void deleteWS(Integer id);

  List<WarningSet> queryIsSendMails(LocalDate localDate, String warnType, String riskLevel);
}
