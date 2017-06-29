package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.GroupChannel;
import java.util.List;

/**
 * Created by lina on 2016-5-5.
 */
public interface GroupChannelDao {
  List<GroupChannel> queryByGroupid(Short groupid);

  void insert(GroupChannel groupChannel);

  void delByGroupId(Short groupid);

  List<GroupChannel> queryByProductCode(String productCode);

  void insertBatch(List<GroupChannel> groupChannels);
}
