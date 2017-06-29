package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.MerchantLimit;

import java.util.List;
import org.joda.time.DateTime;

/**
 * Created by cuixiaofang on 2016-7-19.
 */
public interface MerchantLimitService {

  List<MerchantLimit> queryAll();

  MerchantLimit queryByUserrelateid(String userrelateid, DateTime localDate);


  MerchantLimit insert(MerchantLimit merchantLimit);

  int updateByUserRelateId(MerchantLimit merchantLimit);
}
