package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.ChannelFlowDao;
import com.rkylin.risk.core.entity.ChannelFlow;
import com.rkylin.risk.core.service.ChannelFlowService;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by wjr on 2016-12-28.
 */
@Service
public class ChannelFlowServiceImpl implements ChannelFlowService {
  @Resource
  private ChannelFlowDao channelFlowDao;

  @Override
  public ChannelFlow addChannelFlow(ChannelFlow channelFlow) {
    return channelFlowDao.addChannelFlow(channelFlow);
  }

  @Override
  public void delChannelFlow(String ids) {
    if (!StringUtils.isEmpty(ids)) {
      String[] idArr = ids.split(",");
      for (String id : idArr) {
        channelFlowDao.delete(Integer.valueOf(id));
      }
    }
  }

  @Override
  public void updateChannelFlow(ChannelFlow channelFlow) {
    channelFlowDao.update(channelFlow);
  }

  @Override
  public ChannelFlow get(Integer id) {
    return channelFlowDao.get(id);
  }

  @Override
  public int checkExistProduct(ChannelFlow channelFlow) {
    return channelFlow != null ? this.channelFlowDao.checkExistProduct(channelFlow) : 1;
  }
}
