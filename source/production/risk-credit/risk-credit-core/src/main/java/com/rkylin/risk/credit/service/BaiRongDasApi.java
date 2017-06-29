package com.rkylin.risk.credit.service;

import com.br.bean.DasBean;
import com.br.bean.MerchantBean;
import com.rkylin.risk.credit.service.report.BaiRongDasReportProducer;
import com.rkylin.risk.credit.service.report.ChildReportProducer;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tomalloc on 16-8-22.
 */
public class BaiRongDasApi extends BaiRongApi {
  @Override
  protected MerchantBean wrapperRequestBean(String tokenId, CreditRequestParam requestParam) {
    DasBean dasBean = new DasBean();
    dasBean.setApiType(BrApiType.DAS.toString());
    dasBean.setTokenid(tokenId);
    dasBean.setId(requestParam.getIdNumber());
    dasBean.setName(requestParam.getName());
    dasBean.setCell(requestParam.getMobile());
    dasBean.setBank_id(requestParam.getBankCard());
    //设置套餐
    dasBean.setMeal(getModule());
    return dasBean;
  }

  @Override public List<String> getDefaultModule() {
    List<String> defaultModuleList = new LinkedList<>();
    //学历查询
    defaultModuleList.add("EduLevel");
    return defaultModuleList;
  }

  @Override protected ChildReportProducer childReportProducer(String data) {
    return new BaiRongDasReportProducer(data);
  }
}
