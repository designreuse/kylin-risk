package com.rkylin.risk.spring.handler;

import com.rkylin.risk.spring.handler.page.PageType;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomalloc on 16-7-21.
 */
@Setter
@Getter class UrlSqlConfig {
  /**
   * mybatis id
   */
  private String sqlID;
  /**
   * 分页方式
   */
  private PageType serverDaemon;
  /**
   * 分页最大值,仅限于物理分页
   */
  private int maxPageSize;
}
