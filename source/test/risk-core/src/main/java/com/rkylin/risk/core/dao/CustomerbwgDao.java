package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.CustomebwgList;

import java.util.List;

/**
 * Created by lina on 2016-3-22.
 */
public interface CustomerbwgDao {
  List<CustomebwgList> queryAll();

  CustomebwgList queryById(Integer id);

  CustomebwgList update(CustomebwgList custbwg);

  CustomebwgList addCustmebwg(CustomebwgList custbwg);

  void delCustbwg(Integer id);

  CustomebwgList queryByCustomeridAndType(String customerid, String type);

  CustomebwgList queryByCustomerid(String customerid);
}
