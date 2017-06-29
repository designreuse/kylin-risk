package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.PagingDao;
import com.rkylin.risk.core.service.PagingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/1 version: 1.0
 */
@Service("pagingService")
public class PagingImplServiceImpl implements PagingService {

  @Resource
  private PagingDao pagingDao;

  @Override
  public List query(String sqlId, Map map) {
    return pagingDao.query(sqlId, map);
  }

  @Override
  public List queryPage(String sqlId, int offset, int limit, Map map) {
    return pagingDao.queryPage(sqlId, offset, limit, map);
  }
}
