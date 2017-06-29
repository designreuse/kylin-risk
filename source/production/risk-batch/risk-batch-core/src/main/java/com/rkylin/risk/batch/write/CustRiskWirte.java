package com.rkylin.risk.batch.write;

import com.rkylin.risk.core.entity.CusFactorParam;
import com.rkylin.risk.core.service.CusFactorParamService;
import org.springframework.batch.item.ItemWriter;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508240185 on 2015/11/16.
 */
public class CustRiskWirte implements ItemWriter {
  @Resource
  private CusFactorParamService cusFactorParamService;

  @Override
  public void write(List items) throws Exception {

    for (int i = 0; i < items.size(); i++) {
      CusFactorParam param = (CusFactorParam) items.get(i);
      if (param.getId() == null) {
        cusFactorParamService.insertCusFactorParam(param);
      } else {
        cusFactorParamService.updateByCustid(param);
      }
    }
  }
}
