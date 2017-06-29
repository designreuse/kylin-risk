package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.GroupChannelDao;
import com.rkylin.risk.core.entity.GroupChannel;
import com.rkylin.risk.core.service.GroupChannelService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by lina on 2016-5-17.
 */
@Service
public class GroupChannelServiceImpl implements GroupChannelService {
  @Resource
  private GroupChannelDao groupChannelDao;

  @Override public List<GroupChannel> queryByGroupid(Short groupid) {
    return groupChannelDao.queryByGroupid(groupid);
  }

  @Override public List<GroupChannel> queryByProductCode(String productCode) {
    return groupChannelDao.queryByProductCode(productCode);
  }

  @Override public void delByGroupId(Short groupid) {
    groupChannelDao.delByGroupId(groupid);
  }

  @Override public void insertBatch(List<GroupChannel> groupChannels) {
    groupChannelDao.insertBatch(groupChannels);
  }
}
