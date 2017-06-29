package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.WarningSetDao;
import com.rkylin.risk.core.entity.WarningSet;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 201508031790 on 2015/9/6.
 */
@Repository("warningSetDao")
public class WarningSetDaoImpl extends BaseDaoImpl<WarningSet> implements WarningSetDao {

  @Override
  public List<WarningSet> queryAll() {
    return super.selectAllList();
  }

  @Override
  public WarningSet insert(WarningSet ws) {
    super.add(ws);
    return ws;
  }

  @Override
  public WarningSet queryById(String ws) {
    return super.get(Integer.valueOf(ws));
  }

  @Override
  public WarningSet update(WarningSet ws) {
    super.modify(ws);
    return ws;
  }

  @Override
  public void deleteWS(Integer id) {
    super.del(id);
  }

  @Override
  public List<WarningSet> queryIsSendMails(LocalDate localDate, String warnType, String riskLevel) {
    Map map = new HashMap();
    map.put("localDate", localDate.toString("YYYY-MM-dd"));
    map.put("warnType", warnType);
    map.put("riskLevel", riskLevel);
    return super.selectList("queryIsSendMails", map);
  }
}
