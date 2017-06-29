package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.OperatorLogDao;
import com.rkylin.risk.core.entity.Operatorlog;
import com.rkylin.risk.core.service.OperatorLogService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508240185 on 2015/8/26.
 */
@Service("operatorLogService")
public class OperatorLogServiceImpl implements OperatorLogService {
  @Resource
  private OperatorLogDao operatorLogDao;

  @Override
  public List<Operatorlog> query(Operatorlog log, DateTime endTime) {
    return operatorLogDao.query(log, endTime);
  }

  @Override
  public Operatorlog insert(Short operatorid, String username, String operateobj,
      String operatedes) {
    Operatorlog operatorlog = new Operatorlog();
    operatorlog.setOperatorid(operatorid);
    operatorlog.setUsername(username);
    operatorlog.setOperatedate(DateTime.now());
    operatorlog.setOperateobj(operateobj);
    operatorlog.setOperatedes(operatedes);
    return operatorLogDao.insert(operatorlog);
  }
}
