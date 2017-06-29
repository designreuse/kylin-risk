package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.GroupChannelHis;
import java.util.List;

/**
 * Created by lina on 2016-6-23.
 */
public interface GroupChannelHisService {
  List<GroupChannelHis> queryByGroupid(Short group);
}
