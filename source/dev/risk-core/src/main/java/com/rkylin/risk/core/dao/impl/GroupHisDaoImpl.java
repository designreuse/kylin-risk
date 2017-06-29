package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.GroupHisDao;
import com.rkylin.risk.core.entity.GroupHis;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-7-12.
 */
@Repository
public class GroupHisDaoImpl extends BaseDaoImpl<GroupHis> implements GroupHisDao {
  @Override public GroupHis addGroupHis(GroupHis groupHis) {
    super.add(groupHis);
    return groupHis;
  }

  @Override public GroupHis queryById(Short id) {
    return super.get(id);
  }
}
