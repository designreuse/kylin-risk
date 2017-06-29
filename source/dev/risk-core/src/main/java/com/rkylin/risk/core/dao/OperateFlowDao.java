package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.OperateFlow;
import java.util.List;
import java.util.Map;

/**
 * Created by lina on 2016-8-3.
 */
public interface OperateFlowDao {
  void insert(OperateFlow operateFlow);

  List<OperateFlow> queryByCheckorderid(String checkorderid);

  void updateOperteFlow(OperateFlow operateFlow);

  void updateOperFlowStatus(OperateFlow operateFlow);

  List<OperateFlow> queryByConstidAndStatus(Map parameter);

  void updateMitouResponse(OperateFlow operateFlow);

  List<OperateFlow> queryByCustomerid(String customerid);

  void updateOperFlowByOrder(OperateFlow operateFlow);
}
