package com.rkylin.risk.spring.handler.page;

import java.util.List;
import java.util.Map;

/**
 * Created by tomalloc on 16-7-22.
 */
public interface PagingServiceHandler {
  /**
   * 全部查询
   * @param sqlId
   * @param params
   * @return
   */
  List query(String sqlId, Map params);

  /**
   * 分页查询
   * @param sqlId
   * @param start
   * @param length
   * @param params
   * @return
   */
  List query(String sqlId, int start, int length, Map params);
}
