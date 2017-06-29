package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.MerchantLimitDao;
import com.rkylin.risk.core.entity.MerchantLimit;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuixiaofang on 2016-7-19.
 */
@Repository("merchantLimitDao")
public class MerchantLimitDaoImpl extends BaseDaoImpl<MerchantLimit> implements MerchantLimitDao {
  @Override
  public List<MerchantLimit> queryAll() {
    return super.query("queryAll");
  }

  public MerchantLimit queryByUserrelateid(String userrelateid, DateTime localDate) {
    String localTime = null;
    if (localDate != null) {
      localTime = localDate.toString("YYYY-MM-dd");
    }
    Map map = new HashMap();
    map.put("userrelateid", userrelateid);
    map.put("localDate", localTime);
    List list = super.queryList("queryByUserrelateid", map);
    if (CollectionUtils.isNotEmpty(list)) {
      return (MerchantLimit) list.get(0);
    }
    return null;
  }

  @Override
  public List<MerchantLimit> queryByOrderTime() {
    DateTime dateTime = new DateTime();
    LocalDate date = new LocalDate(dateTime.minusDays(1));
    Map map = new HashMap();
    map.put("localDate", date.toString());
    return super.queryList("queryByOrderTime", map);
  }

  @Override
  public MerchantLimit insert(MerchantLimit merchantLimit) {
    super.add(merchantLimit);
    return merchantLimit;
  }

  @Override
  public int updateByUserRelateId(MerchantLimit merchantLimit) {
    return super.modify("updateByUserRelateId", merchantLimit);
  }
}
