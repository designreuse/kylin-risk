package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.CustomebwgList;

import java.util.List;

/**
 * Created by 201508031790 on 2015/9/7.
 */
public interface CustomerbwgService {

  List<CustomebwgList> queryAll();

  CustomebwgList queryById(Integer id);

  CustomebwgList queryByCustomeridAndType(String customerid, String type);

  String update(String ids, String opertype, String reason, Authorization auth);

  CustomebwgList addCustmebwg(CustomebwgList custbwg);

  void delCustbwg(String ids, Authorization auth, String reason);

  void addBWGFromCustom(String ids, CustomebwgList bwg, Authorization auth);

  String verifybwg(String ids, CustomebwgList bwg, Authorization auth);
}
