package com.rkylin.risk.boss.biz;

import java.util.List;
import java.util.Map;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/1 version: 1.0
 */
public interface PagingBiz {
  List query(String sqlId, Map params);

  List queryPage(String sqlId, int start, int length, Map params);
}
