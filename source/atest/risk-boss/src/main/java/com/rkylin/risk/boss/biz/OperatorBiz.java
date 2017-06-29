package com.rkylin.risk.boss.biz;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Operator;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/13 version: 1.0
 */
public interface OperatorBiz {

  Authorization login(String username, String passwd, String ipAddress);

  Operator addNewOperator(Operator operator, String roleIds,
                          Authorization auth, String productId);

  Operator updatePassWord(Operator oper);

  boolean queryPassWord(String oldpwd, String username);
}
