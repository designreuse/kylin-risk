package com.rkylin.risk.spring.handler.page;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomalloc on 16-7-21.
 */
@Setter
@Getter
public class PageParam {
  /**
   * 开始页
   */
  private int start;
  /**
   * 分页长度
   */
  private int pageSize;

  /**
   * mybatis sql id
   */
  private String sqlID;

  /**
   * 分页设置
   */
  private boolean daemon;

  private String draw;
  /**
   * 请求参数
   */
  private Map<String, String> requestParam;

  private ErrorDesc errorDesc;

  @Setter
  @Getter
  public static class ErrorDesc {
    private int code;
    private String message;
  }
}
