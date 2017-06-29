package com.rkylin.risk.spring.handler.page;

/**
 * Created by tomalloc on 16-7-22.
 */
public enum  PageType {
  /**
   * 分页的设置由浏览器完全决定
   */
  DEFAULT,
  /**
   * 数据全部查出假分页
   */
  ALL,
  /**
   * 数据库的物理分页
   */
  PHYSICAL
}
