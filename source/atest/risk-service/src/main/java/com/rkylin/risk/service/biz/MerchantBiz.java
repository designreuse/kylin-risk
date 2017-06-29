package com.rkylin.risk.service.biz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rkylin.risk.service.bean.MerchantFactor;
import java.io.IOException;

/**
 * Created by lina on 2016-8-15.
 */
public interface MerchantBiz {
  String newMerchantmsgHandle(MerchantFactor merchantFactor) throws IOException;

  String queryMerchantmsgHandle(String checkorderid) throws JsonProcessingException;

  String calcMerchantMsgHandle(String checkorderid) throws JsonProcessingException;
}
