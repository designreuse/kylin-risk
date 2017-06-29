package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.OperateFlow;
import java.util.List;
import java.util.Map;

/**
 * Created by lina on 2016-8-3.
 */
public interface OperateFlowService {
  void insert(OperateFlow operateFlow);

  OperateFlow queryByCheckorderid(String checkorderid);

  void updateOperteFlow(OperateFlow operateFlow);

  void updateOperFlowStatus(OperateFlow operateFlow);

  List<OperateFlow> queryByConstidAndStatus(Map parameter);

  void updateMitouResponse(OperateFlow operateFlow);

  OperateFlow queryByCustomerid(String customerid);

  void updateOperFlowByOrder(OperateFlow operateFlow);
}
