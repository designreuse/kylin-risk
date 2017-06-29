package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.OperateFlowDao;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.OperateFlowService;
import java.util.List;
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
}
