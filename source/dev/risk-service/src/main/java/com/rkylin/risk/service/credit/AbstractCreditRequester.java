package com.rkylin.risk.service.credit;

import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.service.bean.CreditRequestEntity;

/**
 * Created by tomalloc on 16-12-14.
 */
public abstract class AbstractCreditRequester implements CreditRequester {

  public long fetchCreditModue(String[] module){
    return -1;
  }

  protected abstract CreditResult request(CreditResult creditResult,String[] module);

  @Override public CreditResult requestCreditResult(CreditRequestEntity requestEntity, String[] module) {
    CreditResult creditResult=new CreditResult();
    creditResult.setUserName(requestEntity.getName());
    creditResult.setIdNumber(requestEntity.getIdNumber());
    creditResult.setMobile(requestEntity.getMobile());
    creditResult.setBankCard(requestEntity.getBankCard());
    creditResult.setUserId(requestEntity.getUserId());
    creditResult.setChannel(requestEntity.getChannel());
    creditResult.setOrderId(requestEntity.getOrderId());
    creditResult.setWorkflowId(requestEntity.getWorkflowId());
    return request(creditResult,module);
  }
}
