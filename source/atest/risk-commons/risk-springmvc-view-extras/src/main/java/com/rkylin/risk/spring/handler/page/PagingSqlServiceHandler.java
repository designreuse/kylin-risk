package com.rkylin.risk.spring.handler.page;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tomalloc on 16-7-22.
 */
public abstract class PagingSqlServiceHandler implements PageServiceHandler{
  /**
   * 全部查询
   * @param sqlId
   * @param params
   * @return
   */
  abstract List query(String sqlId, Map params);

  /**
   * 分页查询
   * @param sqlId
   * @param start
   * @param length
   * @param params
   * @return
   */
  abstract List query(String sqlId, int start, int length, Map params);

  @Override public List query(HttpServletRequest request) {
    return null;
  }
}
