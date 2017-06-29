package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.FactorLibrary;
import java.util.List;

/**
 * Created by lina on 2016-7-28.
 */
public interface FactorLibraryService {
  FactorLibrary queryById(Short id);

  FactorLibrary addFactoryLibrary(FactorLibrary factorLibrary);

  void update(FactorLibrary factorLibrary);

  List<FactorLibrary> queryByType(String type);

  void deleteById(Short id);

  void updateFactorStatus(String ids, String status);
  /**
   * 验证课程类型名称唯一性
   */
  int checkExistCode(FactorLibrary factorLibrary);
}
