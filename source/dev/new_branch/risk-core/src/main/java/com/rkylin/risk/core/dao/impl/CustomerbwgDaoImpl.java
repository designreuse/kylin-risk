package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CustomerbwgDao;
import com.rkylin.risk.core.entity.CustomebwgList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-3-22.
 */
@Repository("customerbwgDao")
public class CustomerbwgDaoImpl extends BaseDaoImpl<CustomebwgList> implements CustomerbwgDao {

  @Override
  public List<CustomebwgList> queryAll() {
    return super.selectAllList();
  }

  @Override
  public CustomebwgList queryById(Integer id) {
    return super.get(id);
  }

  @Override
  public CustomebwgList update(CustomebwgList custbwg) {
    super.modify(custbwg);
    return custbwg;
  }

  @Override
  public CustomebwgList addCustmebwg(CustomebwgList custbwg) {
    super.add(custbwg);
    return custbwg;
  }

  @Override
  public void delCustbwg(Integer id) {
    super.del(id);
  }

  @Override
  public CustomebwgList queryByCustomeridAndType(String customerid, String type) {
    LocalDate localDate = new LocalDate();
    Map map = new HashMap();
    map.put("customerid", customerid);
    map.put("type", type);
    map.put("localDate", localDate.toString("yyyy-MM-dd"));
    List<CustomebwgList> list = super.selectList("queryByCustomeridAndType", map);
    if (!list.isEmpty()) {
      return list.get(0);
    } else {
      return null;
    }
  }

  @Override public CustomebwgList queryByCustomerid(String customerid) {
    List<CustomebwgList> list = super.selectList("queryByCustomerid", customerid);
    if (!list.isEmpty()) {
      return list.get(0);
    } else {
      return null;
    }
  }
}
