package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.GroupChannel;
import java.util.List;

/**
 * Created by lina on 2016-5-17.
 */
public interface GroupChannelService {
  List<GroupChannel> queryByGroupid(Short groupid);

  List<GroupChannel> queryByProductCode(String productCode);

  void delByGroupId(Short groupid);

  void insertBatch(List<GroupChannel> groupChannels);
}
