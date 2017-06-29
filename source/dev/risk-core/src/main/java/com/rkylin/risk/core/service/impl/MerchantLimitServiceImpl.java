package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.MerchantLimitDao;
import com.rkylin.risk.core.entity.MerchantLimit;
import com.rkylin.risk.core.service.MerchantLimitService;
import java.util.List;
import javax.annotation.Resource;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

/**
 * Created by cuixiaofang on 2016-7-19.
 */
@Service("merchantLimitService")
public class MerchantLimitServiceImpl implements MerchantLimitService {
  @Resource
  private MerchantLimitDao merchantLimitDao;

  @Override
  public List<MerchantLimit> queryAll() {
    return merchantLimitDao.queryAll();
  }

  @Override
  public MerchantLimit queryByUserrelateid(String userrelateid, DateTime localDate) {
    return merchantLimitDao.queryByUserrelateid(userrelateid, localDate);
  }


  @Override
  public MerchantLimit insert(MerchantLimit merchantLimit) {
    return merchantLimitDao.insert(merchantLimit);
  }

  @Override
  public int updateByUserRelateId(MerchantLimit merchantLimit) {
    return merchantLimitDao.updateByUserRelateId(merchantLimit);
  }
}
