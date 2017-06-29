package com.rkylin.risk.batch.processor;

import com.rkylin.risk.core.entity.CusFactorParam;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.service.CusFactorParamService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by 201508240185 on 2015/11/16.
 */
@Slf4j
public class CustRiskProcessor implements ItemProcessor {

  @Resource
  private CusFactorParamService cusFactorParamService;

  @Override
  public Object process(Object item) throws Exception {
    Customerinfo customer = (Customerinfo) item;
    try {
      CusFactorParam param = cusFactorParamService.queryByCustomerid(customer.getCustomerid());
      //当客户因子参数不存在，或历史高风险参数不为true，则计算客户历史高风险参数
      if (param == null || !"true".equals(param.getTrxwrongflag())) {
        //客户因子参数为空，则新建
        if (param == null) {
          param = new CusFactorParam();
          param.setCustid(customer.getId());
        } else {
          //不存在高风险订单，历史高风险参数为false
          param.setTrxwrongflag("false");
        }
      }
      log.info("客户" + customer.getCustomerid() + ",历史高风险计算成功");
      return param;
    } catch (Exception e) {
      log.info("客户" + customer.getCustomerid() + ",历史高风险计算失败");
      throw e;
    }
  }
}
