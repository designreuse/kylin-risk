package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.FactorTempl;
import java.util.List;

/**
 * Created by lina on 2016-5-9.
 */
public interface FactorTemplDao {
  /**
   * 查询因子模板
   */

  List<FactorTempl> queryByGroup(Short groupid);

  FactorTempl findById(Short id);

  FactorTempl addTempl(FactorTempl factorTempl);

  void updateFactorTempl(FactorTempl factorTempl);

  void delete(Short id);
}
