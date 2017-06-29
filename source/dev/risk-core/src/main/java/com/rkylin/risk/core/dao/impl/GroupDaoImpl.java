package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.GroupDao;
import com.rkylin.risk.core.entity.Group;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lina on 2016-7-11.
 */
@Repository
public class GroupDaoImpl extends BaseDaoImpl<Group> implements GroupDao {
  @Override
  public void delete(Short id) {
    super.del(id);
  }

  @Override
  public Group queryById(Short id) {
    return super.get(id);
  }

  @Override
  public void update(Group group) {
    super.modify(group);
  }

  @Override
  public Group addGroup(Group group) {
    super.add(group);
    return group;
  }

  @Override
  public List<Group> queryAllGroup() {
    return super.selectList("queryAllGroup");
  }

  @Override
  public Group queryByTimeAndStatus(String issueartifactid, String issuegroupid) {
    Map map = new HashMap();
    map.put("localDate", LocalDate.now());
    map.put("issueartifactid", issueartifactid);
    map.put("issuegroupid", issuegroupid);
    List list = super.selectList("queryByTimeAndStatus", map);
    if (CollectionUtils.isNotEmpty(list)) {
      return (Group) list.get(0);
    } else {
      return null;
    }
  }

  @Override public Group queryByChannelAndServiceType(String channelCode, String serviceType) {
    Map map = new HashMap();
    map.put("channelCode", channelCode);
    map.put("serviceType", serviceType);
    List<Group> groups = super.selectList("queryByChannelAndStatus", map);
    if (!groups.isEmpty()) {
      return groups.get(0);
    }
    return null;
  }

  @Override public Group queryByProductAndServiceType(String productCode, String serviceType) {
    Map map = new HashMap();
    map.put("productCode", productCode);
    map.put("serviceType", serviceType);
    List<Group> groups = super.selectList("queryByProductAndStatus", map);
    if (!groups.isEmpty()) {
      return groups.get(0);
    }
    return null;
  }
}
