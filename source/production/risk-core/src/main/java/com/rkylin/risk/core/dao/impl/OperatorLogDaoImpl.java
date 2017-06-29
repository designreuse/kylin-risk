package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.OperatorLogDao;
import com.rkylin.risk.core.entity.Operatorlog;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 201508240185 on 2015/8/26.
 */
@Repository("operatorLogDao")
public class OperatorLogDaoImpl extends BaseDaoImpl<Operatorlog> implements OperatorLogDao {
  @Override
  public List<Operatorlog> query(Operatorlog log, DateTime endTime) {
    Map map = Maps.newHashMap();
    map.put("log", log);
    map.put("endTime", endTime);

    return super.query("queryByOperLog", map);
  }

  @Override
  public Operatorlog insert(Operatorlog operatorlog) {
    super.add(operatorlog);
    return operatorlog;
  }
}
