package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.FactorTempl;
import java.util.List;

/**
 * Created by lina on 2016-5-23.
 */
public interface FactorTemplService {
  void add(String groupid, String libraryid);

  void updateFactorTempl(FactorTempl factorTempl);

  FactorTempl findById(Short id);

  void deleteFactorTempl(String ids);

  List<FactorTempl> queryByGroup(Short groupid);
}
