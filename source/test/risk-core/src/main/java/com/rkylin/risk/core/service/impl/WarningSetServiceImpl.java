package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.OperatorDao;
import com.rkylin.risk.core.dao.WarningSetDao;
import com.rkylin.risk.core.entity.Operator;
import com.rkylin.risk.core.entity.WarningSet;
import com.rkylin.risk.core.service.WarningSetService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508031790 on 2015/9/6.
 */
@Service("warningSetService")
@Slf4j
public class WarningSetServiceImpl implements WarningSetService {

  @Resource
  private WarningSetDao warningSetDao;
  @Resource
  private OperatorDao operatorDao;

  @Override
  public List<WarningSet> queryAll() {
    return warningSetDao.queryAll();
  }

  @Override
  public void addWarningSet(WarningSet ws, String operatorIds) {
    String[] operArray = operatorIds.split(",");
    for (int i = 0; i < operArray.length; i++) {
      Operator oper = operatorDao.queryById(Short.valueOf(operArray[i]));
      if (oper != null) {
        WarningSet warnset = new WarningSet();
        warnset.setOperatorid(oper.getId());
        warnset.setUsername(oper.getUsername());
        warnset.setEffdate(ws.getEffdate());
        warnset.setRisklevel(ws.getRisklevel());
        warnset.setWarntype(ws.getWarntype());
        if ("on".equals(ws.getStatus())) {
          warnset.setStatus(Constants.ACTIVE);
        } else {
          warnset.setStatus(Constants.INACTIVE);
        }

        warningSetDao.insert(warnset);
      }
    }
  }

  @Override
  public WarningSet queryById(String ws) {
    return warningSetDao.queryById(ws);
  }

  @Override
  public WarningSet update(WarningSet ws) {
    if (ws.getStatus() != null && "on".equals(ws.getStatus())) {
      ws.setStatus(Constants.ACTIVE);
    } else {
      ws.setStatus(Constants.INACTIVE);
    }
    return warningSetDao.update(ws);
  }

  @Override
  public Boolean delWarnset(String ids) {
    String[] idsarray = ids.split(",");
    try {
      for (int i = 0; i < idsarray.length; i++) {
        warningSetDao.deleteWS(Integer.valueOf(idsarray[i]));
      }
      return true;
    } catch (Exception e) {
      log.info(e.getMessage(), e);
      return false;
    }
  }

  @Override
  public List<WarningSet> queryIsSendMails(LocalDate localDate, String warnType, String riskLevel) {
    return warningSetDao.queryIsSendMails(localDate, warnType, riskLevel);
  }
}
