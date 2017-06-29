package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Group;
import com.rkylin.risk.core.entity.GroupVersion;

/**
 * Created by lina on 2016-7-20.
 */
public interface GroupVersionService {
  GroupVersion insert(GroupVersion groupVersion);

  void update(GroupVersion groupVersion);

  GroupVersion queryById(Short id);

  void insertGroupVersion(Group group, Authorization auth);

  String loadRuleGroupVersionFromCache(String groupId, String artifactId);

  String queryRuleGroupVersion(String groupId, String artifactId);
}
