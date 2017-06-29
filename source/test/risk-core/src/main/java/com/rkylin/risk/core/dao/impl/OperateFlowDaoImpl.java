package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.OperateFlowDao;
import com.rkylin.risk.core.entity.OperateFlow;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-8-3.
 */
@Repository
public class OperateFlowDaoImpl extends BaseDaoImpl<OperateFlow> implements OperateFlowDao {
  @Override public void insert(OperateFlow operateFlow) {
    super.add(operateFlow);
  }

  @Override public List<OperateFlow> queryByCheckorderid(String checkorderid) {
    return super.selectList("queryByCheckorderid", checkorderid);
  }

  @Override public void updateOperteFlow(OperateFlow operateFlow) {
    super.modify(operateFlow);
  }

  @Override public void updateOperFlowStatus(OperateFlow operateFlow) {
    super.modify("updateOperFlowStatus", operateFlow);
  }

  @Override public List<OperateFlow> queryByConstidAndStatus(Map map) {

    return super.selectList("queryByConstidAndStatus", map);
  }

  @Override public void updateMitouResponse(OperateFlow operateFlow) {
    super.modify("updateMitouResponse", operateFlow);
  }

  @Override public List<OperateFlow> queryByCustomerid(String customerid) {
    return super.selectList("queryByCustomerid", customerid);
  }

  @Override public void updateOperFlowByOrder(OperateFlow operateFlow) {
    super.modify("updateOperFlowByOrder",operateFlow);
  }
}
