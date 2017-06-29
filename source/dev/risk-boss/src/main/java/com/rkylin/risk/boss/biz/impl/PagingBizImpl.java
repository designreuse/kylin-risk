package com.rkylin.risk.boss.biz.impl;

import com.rkylin.risk.boss.biz.PagingBiz;
import com.rkylin.risk.core.service.PagingService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/1 version: 1.0
 */
@Component("pagingBiz")
public class PagingBizImpl implements PagingBiz {
  @Resource
  private PagingService pagingService;

  @Override
  public List query(String sqlId, Map params) {
    return pagingService.query(sqlId, params);
  }

  @Override
  public List queryPage(String sqlId, int start, int length, Map params) {
    return pagingService.queryPage(sqlId, start, length, params);
  }
}
