package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Customerinfo;

import java.util.List;

/**
 * Created by lina on 2016-3-22.
 */
public interface CustomerinfoService {
  /**
   * 根据商户编号查找商户信息
   *
   * @return Customerinfo
   */
  Customerinfo queryOne(String customerid);

  /**
   * 根据给定的条件查询所有的商户信息
   *
   * @return List<Customerinfo>
   */
  List<Customerinfo> queryAll(Customerinfo customerinfo);

  Customerinfo insert(Customerinfo customerinfo);

  void addCustomerinfoBatch(List<Customerinfo> customerinfos);

  Integer addCustomerInfo(Customerinfo customerinfo);

  void update(Customerinfo customerinfo);
}

