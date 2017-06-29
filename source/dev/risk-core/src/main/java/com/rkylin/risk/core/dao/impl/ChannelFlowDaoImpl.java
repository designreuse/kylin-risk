package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.ChannelFlowDao;
import com.rkylin.risk.core.entity.ChannelFlow;
import org.springframework.stereotype.Repository;

/**
 * Created by wjr on 2016-12-28.
 */
@Repository
public class ChannelFlowDaoImpl extends BaseDaoImpl<ChannelFlow> implements
    ChannelFlowDao {

  @Override
  public ChannelFlow addChannelFlow(ChannelFlow channelFlow) {
    super.add(channelFlow);
    return channelFlow;
  }

  @Override
  public void delete(Integer id) {
    super.del(id);
  }

  @Override
  public void update(ChannelFlow channelFlow) {
      super.modify(channelFlow);
  }

  @Override
  public ChannelFlow get(Integer id) {
    return super.get(id);
  }

  @Override
  public int checkExistProduct(ChannelFlow channelFlow) {
    return super.selectList("checkExistProduct", channelFlow).size();
  }
}
