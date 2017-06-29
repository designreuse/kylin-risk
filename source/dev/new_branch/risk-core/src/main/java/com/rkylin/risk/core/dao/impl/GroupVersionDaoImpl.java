package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.GroupVersionDao;
import com.rkylin.risk.core.entity.GroupVersion;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-7-20.
 */
@Repository
public class GroupVersionDaoImpl extends BaseDaoImpl<GroupVersion> implements GroupVersionDao {
  @Override public GroupVersion insert(GroupVersion groupVersion) {
    super.add(groupVersion);
    return groupVersion;
  }

  @Override public void update(GroupVersion groupVersion) {
    super.modify(groupVersion);
  }

  @Override public void updateVersionSta(GroupVersion groupVersion) {
    super.modify("updateVersionSta", groupVersion);
  }

  @Override public GroupVersion queryById(Short id) {
    return super.get(id);
  }

  @Override public GroupVersion query(String group, String artifactid, String version) {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("group", group);
    map.put("artifactid", artifactid);
    map.put("version", version);
    return selectOne("queryByGA", map);
  }
}
