package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.GroupHis;

/**
 * Created by lina on 2016-7-12.
 */
public interface GroupHisDao {
  GroupHis queryById(Short id);

  GroupHis addGroupHis(GroupHis groupHis);
}
