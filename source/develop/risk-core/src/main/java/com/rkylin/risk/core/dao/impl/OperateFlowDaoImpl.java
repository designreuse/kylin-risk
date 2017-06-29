package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.OperateFlowDao;
import com.rkylin.risk.core.entity.OperateFlow;
import java.util.List;
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
    return super.query("queryByCheckorderid", checkorderid);
  }
}
