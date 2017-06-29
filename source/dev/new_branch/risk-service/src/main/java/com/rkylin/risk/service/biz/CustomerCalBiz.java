package com.rkylin.risk.service.biz;

import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.dto.ScoreRuleBean;
import com.rkylin.risk.operation.bean.CustomerMsg;
import com.rkylin.risk.operation.bean.ResultInfo;

/**
 * Created by lina on 2016-6-17.
 */
public interface CustomerCalBiz {

  ResultBean calCustomerFactor(ScoreRuleBean customer);

  /**
   * 客户信息处理
   * @param customerMsg
   * @return
   */
  ResultInfo customerInfoHandle(CustomerMsg customerMsg);
}
