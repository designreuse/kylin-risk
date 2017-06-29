package com.rkylin.risk.core.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomalloc on 16-12-12.
 */
@Setter
@Getter
public class CreditParam {
  /**
   * 是否重新查询
   */
  private boolean isQueryAgain;

  /**
   * 查询者
   */
  private String querier;


  /**
   * 请求数据
   */
  private List<CreditRequestEntity> data;

}
