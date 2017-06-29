package com.rkylin.risk.service.biz;

import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.entity.Group;

/**
 * Created by lina on 2016-7-26.
 */
public interface LogicRuleCalBiz {
  ResultBean calLogicRule(LogicRuleBean logic,Group group);

}
