package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.GroupChannelHisDao;
import com.rkylin.risk.core.entity.GroupChannelHis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-6-23.
 */
@Repository
public class GroupChannelHisDaoImpl extends BaseDaoImpl<GroupChannelHis>
    implements GroupChannelHisDao {
  @Override public List<GroupChannelHis> queryByGroupid(Short groupid) {
    return super.query("queryByGroupid", groupid);
  }

  @Override public void insertFromGroupChannel(GroupChannelHis groupChannelHis) {
    Map map = new HashMap();
    map.put("groupid", groupChannelHis.getGrouphisid());
    super.add("insertFromGroupChannel", groupChannelHis);
  }
}
