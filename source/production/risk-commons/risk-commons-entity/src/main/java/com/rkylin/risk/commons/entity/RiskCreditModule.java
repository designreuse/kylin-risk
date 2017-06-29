package com.rkylin.risk.commons.entity;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author qiuxian
 * @create 2016-09-28 15:04
 **/
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class RiskCreditModule implements Serializable {

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
  private String realModuleId;
}
