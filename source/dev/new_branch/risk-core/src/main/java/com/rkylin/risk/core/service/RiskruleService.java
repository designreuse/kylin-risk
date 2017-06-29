package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Riskrule;

import java.util.List;

/**
 * Created by 201506290344 on 2015/8/24.
 */
public interface RiskruleService {

  List<Riskrule> queryAll(Riskrule riskrule);

  Riskrule queryOne(Integer id);

  Riskrule insert(Riskrule riskrule, Authorization auth);

  void modify(Riskrule riskrule, Authorization auth);

  void modifyStatus(String ids, String setStatus, Authorization auth);

  void delete(String ids, Authorization auth);
}
