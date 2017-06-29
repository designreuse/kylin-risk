package com.rkylin.risk.credit.service;

import com.br.bean.MerchantBean;
import com.br.bean.TerBean;
import com.rkylin.risk.credit.service.report.BaiRongTerReportProducer;
import com.rkylin.risk.credit.service.report.ChildReportProducer;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tomalloc on 16-8-22.
 */
public class BaiRongTerApi extends BaiRongApi {
  @Override
  protected MerchantBean wrapperRequestBean(String tokenId, CreditRequestParam requestParam) {
      TerBean terBean = new TerBean();
      terBean.setApiType(BrApiType.TER.toString());
      terBean.setTokenid(tokenId);
      terBean.setGid("");
      terBean.setId(requestParam.getIdNumber());
      terBean.setName(requestParam.getName());
      terBean.setCell(requestParam.getMobile());
      terBean.setBank_id(requestParam.getBankCard());
      //设置套餐
      terBean.setMeal(getModule());
      return terBean;
  }

  @Override public List<String> getDefaultModule() {
    List<String> defaultModuleList = new LinkedList<>();
    //特殊名单
    defaultModuleList.add("SpecialList_c");
    defaultModuleList.add("ApplyLoan");
    //defaultModuleList.add("RuleSpecialList");
    //多次查询
    //defaultModuleList.add("RuleApplyLoan");
    return defaultModuleList;
  }

  @Override protected ChildReportProducer childReportProducer(String data) {
    return new BaiRongTerReportProducer(data);
  }

}
