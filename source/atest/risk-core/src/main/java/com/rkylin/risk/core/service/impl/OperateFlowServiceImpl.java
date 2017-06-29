package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.OperateFlowDao;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.OperateFlowService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by lina on 2016-8-3.
 */
@Service
public class OperateFlowServiceImpl implements OperateFlowService {
  @Resource
  private OperateFlowDao operateFlowDao;

  @Override public void insert(OperateFlow operateFlow) {
    operateFlowDao.insert(operateFlow);
  }

  @Override public OperateFlow queryByCheckorderid(String checkorderid) {
    List<OperateFlow> operateFlows = operateFlowDao.queryByCheckorderid(checkorderid);
    return !operateFlows.isEmpty() ? operateFlows.get(0) : null;
  }

  @Override public void updateOperteFlow(OperateFlow operateFlow) {
    operateFlowDao.updateOperteFlow(operateFlow);
  }

  @Override public void updateOperFlowStatus(OperateFlow operateFlow) {
    operateFlowDao.updateOperFlowStatus(operateFlow);
  }

  public OperateFlowServiceImpl() {
    super();
  }

  @Override public List<OperateFlow> queryByConstidAndStatus(Map parameter) {
    List<OperateFlow> operateFlows = operateFlowDao.queryByConstidAndStatus(parameter);
    return operateFlows;
  }

  @Override public void updateMitouResponse(OperateFlow operateFlow) {
    operateFlowDao.updateMitouResponse(operateFlow);
  }

  @Override public OperateFlow queryByCustomerid(String customerid) {
    List<OperateFlow> operateFlows = operateFlowDao.queryByCustomerid(customerid);
    return !operateFlows.isEmpty() ? operateFlows.get(0) : null;
  }
}
