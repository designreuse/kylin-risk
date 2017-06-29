package com.rkylin.risk.service.credit;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.enumtype.CreditProductType;
import com.rkylin.risk.core.service.CreditResultService;
import com.rkylin.risk.service.bean.CreditRequestEntity;
import java.util.List;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import upa.client.UPAClient;

/**
 * Created by tomalloc on 16-11-30.
 */
public class UnionPayAdvisorsRequester extends AbstractCreditRequester {

  private String resourceId;

  @Resource
  private CreditResultService creditResultService;

  public UnionPayAdvisorsRequester(String resourceId) {
    this.resourceId = resourceId;
  }

  @Resource
  private UPAClient upaClient;


  @Override
  public List<CreditResult> queryCreditResult(CreditRequestEntity requestEntity, String[] module) {
    return creditResultService.queryUnionPayCreditResult(requestEntity.getBankCard());
  }

  @Override protected CreditResult request(CreditResult creditResult,String[] module){
    String bankCard=creditResult.getBankCard();
    JSONObject jsonObject = upaClient.getCommonScoresByAccountNo(bankCard, resourceId);
    String code = jsonObject.getString("error_code");
    //错误码描述
    String riskCode="99999";
    if ("0".equals(code) || "10005".equals(code)) {
      //TODO 错误码处理
      riskCode="10000";
    }
    creditResult.setResult(jsonObject.toString());
    creditResult.setCreditProduct(CreditProductType.UNIONPAY.name());
    creditResult.setCreditCode(code);
    creditResult.setRiskCode(riskCode);
    creditResult.setRequestTime(new DateTime());
    //银联智策不存在模块
    creditResult.setCreditModuleId(-1);
    //TODO 单价处理
    creditResult.setPrice(new Amount(3d));
    return creditResult;
  }
}
