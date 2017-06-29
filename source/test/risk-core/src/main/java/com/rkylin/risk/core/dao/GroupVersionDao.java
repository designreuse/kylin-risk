package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.GroupVersion;

/**
 * Created by lina on 2016-7-20.
 */
public interface GroupVersionDao {
  GroupVersion insert(GroupVersion groupVersion);

  void update(GroupVersion groupVersion);

  void updateVersionSta(GroupVersion groupVersion);

  GroupVersion queryById(Short id);

  GroupVersion query(String group, String artifactid, String version);
}
