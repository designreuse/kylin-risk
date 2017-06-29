package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.MerchantLimit;

import java.util.List;
import org.joda.time.DateTime;

/**
 * Created by cuixiaofang on 2016-7-19.
 */
public interface MerchantLimitDao {

  List<MerchantLimit> queryAll();

  MerchantLimit queryByUserrelateid(String userrelateid, DateTime localDate);

  List<MerchantLimit> queryByOrderTime();

  MerchantLimit insert(MerchantLimit merchantLimit);

  int updateByUserRelateId(MerchantLimit merchantLimit);
}
