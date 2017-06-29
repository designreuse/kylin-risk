package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.RiskruleDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Riskrule;
import com.rkylin.risk.core.service.OperatorLogService;
import com.rkylin.risk.core.service.RiskruleService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 201506290344 on 2015/8/24.
 */
@Service("riskruleService")
public class RiskruleServiceImpl implements RiskruleService {

  @Resource
  private RiskruleDao riskruleDao;
  @Resource
  private OperatorLogService operatorLogService;

  @Override
  public List<Riskrule> queryAll(Riskrule riskrule) {
    return riskruleDao.queryAll(riskrule);
  }

  @Override
  public Riskrule queryOne(Integer id) {
    return riskruleDao.queryOne(id);
  }

  @Override
  public Riskrule insert(Riskrule riskrule, Authorization auth) {
    riskrule.setUserid(auth.getUserId());
    riskrule.setUsername(auth.getUsername());
    riskrule.setUpdatetime(DateTime.now());
    riskrule.setCreatetime(DateTime.now());
    riskrule.setStatus(Constants.ACTIVE);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), "Riskrule", "添加");
    return riskruleDao.insert(riskrule);
  }

  @Override
  public void modify(Riskrule riskrule, Authorization auth) {
    if (riskrule.getStatus() == null || "".equals(riskrule.getStatus())) {
      riskrule.setStatus(Constants.INACTIVE);
    }
    riskrule.setUserid(auth.getUserId());
    riskrule.setUsername(auth.getUsername());
    riskrule.setUpdatetime(DateTime.now());
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), "Riskrule", "更新");
    riskruleDao.update(riskrule);
  }

  @Override
  public void modifyStatus(String ids, String setStatus, Authorization auth) {
    String[] idArry = ids.split(",");
    List<String> ruleIds = Arrays.asList(idArry);
    for (String id : ruleIds) {
      Riskrule riskrule = new Riskrule();
      riskrule.setId(Integer.parseInt(id));
      riskrule.setUserid(auth.getUserId());
      riskrule.setUsername(auth.getUsername());
      riskrule.setUpdatetime(DateTime.now());
      if ("Active".equals(setStatus)) {
        riskrule.setStatus(Constants.ACTIVE);
      }
      if ("Inactive".equals(setStatus)) {
        riskrule.setStatus(Constants.INACTIVE);
      }
      riskruleDao.modifyStatus(riskrule);
      operatorLogService.insert(auth.getUserId(), auth.getUsername(), "Riskrule", "修改状态");
    }
  }

  @Override
  public void delete(String ids, Authorization auth) {
    String[] idArry = ids.split(",");
    List<String> ruleIds = Arrays.asList(idArry);
    for (String id : ruleIds) {
      riskruleDao.delete(Integer.parseInt(id));
      operatorLogService.insert(auth.getUserId(), auth.getUsername(), "Riskrule", "删除");
    }
  }
}
