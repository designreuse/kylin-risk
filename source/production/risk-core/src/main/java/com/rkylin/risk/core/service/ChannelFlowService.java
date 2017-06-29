package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.ChannelFlow;

/**
 * Created by wjr on 2016-12-28.
 */
public interface ChannelFlowService {
  ChannelFlow addChannelFlow(ChannelFlow channelFlow);

  void delChannelFlow(String ids);

  void updateChannelFlow(ChannelFlow channelFlow);

  ChannelFlow get(Integer id);

  int checkExistProduct(ChannelFlow channelFlow);
}
