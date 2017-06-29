package com.rkylin.risk.core.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomalloc on 16-12-13.
 */
@Setter
@Getter
public class CreditModule implements Entity {
  /**
   * 唯一标示
   */
  private long id;
  /**
   * 查询模块的名称
   */
  private String moduleQueryName;
  /**
   * 真实保存在数据库中的模块名称
   */
  private String moduleName;
  /**
   * 真实保存在数据库中对应的模块的ID
   */
  private Long realModuleId;

  /**
   * 是否原子查询模块
   */
  private boolean isAtomicQuery;
}
