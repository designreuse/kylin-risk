package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.GroupChannelHis;
import java.util.List;

/**
 * Created by lina on 2016-6-23.
 */
public interface GroupChannelHisDao {
  List<GroupChannelHis> queryByGroupid(Short groupid);

  void insertFromGroupChannel(GroupChannelHis groupChannelHis);
}
