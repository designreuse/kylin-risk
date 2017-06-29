package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.ChannelFlow;

/**
 * Created by wjr on 2016-12-28.
 */
public interface ChannelFlowDao {
  ChannelFlow addChannelFlow(ChannelFlow channelFlow);

  void delete(Integer id);

  void update(ChannelFlow channelFlow);

  ChannelFlow get(Integer id);

  int checkExistProduct(ChannelFlow channelFlow);
}
