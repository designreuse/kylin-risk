package com.rkylin.risk.boss.biz;

import com.rkylin.risk.core.dto.CreditParam;

/**
 * Created by Administrator on 2016-12-19.
 */
public interface CreditBiz {
  String requestProxy(String url,CreditParam creditParam);
}
