package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.OperateFlow;
import java.util.List;

/**
 * Created by lina on 2016-8-3.
 */
public interface OperateFlowDao {
  void insert(OperateFlow operateFlow);

  List<OperateFlow> queryByCheckorderid(String checkorderid);
}
