package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.OperateFlow;

/**
 * Created by lina on 2016-8-3.
 */
public interface OperateFlowService {
  void insert(OperateFlow operateFlow);

  OperateFlow queryByCheckorderid(String checkorderid);

  void updateOperteFlow(OperateFlow operateFlow);
}
