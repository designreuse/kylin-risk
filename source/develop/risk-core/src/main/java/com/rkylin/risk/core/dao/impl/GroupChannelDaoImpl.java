package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.GroupChannelDao;
import com.rkylin.risk.core.entity.GroupChannel;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-5-5.
 */
@Repository("ruleChannelDao")
public class GroupChannelDaoImpl extends BaseDaoImpl<GroupChannel> implements GroupChannelDao {

  @Override public void insert(GroupChannel groupChannel) {
    super.add(groupChannel);
  }

  @Override public List<GroupChannel> queryByGroupid(Short groupid) {
    return super.query("queryByGroupid", groupid);
  }

  @Override public void delByGroupId(Short groupid) {
    super.del("delByGroupId", groupid);
  }

  @Override public List<GroupChannel> queryByProductCode(String productCode) {
    return super.query("queryByProductCode", productCode);
  }

  @Override public void insertBatch(List<GroupChannel> groupChannels) {
    if (groupChannels != null && !groupChannels.isEmpty()) {
      super.addBatch("insertBatch", groupChannels);
    }
  }
}
