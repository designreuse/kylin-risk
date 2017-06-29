package com.rkylin.risk.core.dao;

import java.util.List;
import java.util.Map;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/1 version: 1.0
 */
public interface PagingDao {
  List query(String sqlId, Map map);

  List queryPage(String sqlId, int offset, int limit, Map map);
}
