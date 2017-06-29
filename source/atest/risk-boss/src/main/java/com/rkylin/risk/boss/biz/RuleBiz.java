package com.rkylin.risk.boss.biz;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.GroupVersion;

/**
 * Created by lina on 2016-6-15.
 */
public interface RuleBiz {
  void creategeneratingRule(GroupVersion groupVersion, Authorization auth);
}
