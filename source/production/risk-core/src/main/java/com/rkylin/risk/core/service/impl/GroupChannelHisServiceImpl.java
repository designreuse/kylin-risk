package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.GroupChannelHisDao;
import com.rkylin.risk.core.entity.GroupChannelHis;
import com.rkylin.risk.core.service.GroupChannelHisService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by lina on 2016-6-23.
 */
@Service
public class GroupChannelHisServiceImpl implements GroupChannelHisService {
  @Resource
  private GroupChannelHisDao groupChannelHisDao;
  @Override public List<GroupChannelHis> queryByGroupid(Short groupid) {
    return groupChannelHisDao.queryByGroupid(groupid);
  }
}
