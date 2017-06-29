package com.rkylin.risk.service.credit;

import com.google.common.base.Optional;
import com.rkylin.risk.core.entity.CreditResult;
import java.util.Map;

/**
 * 百融 ter api Created by tomalloc on 16-12-13.
 */
public class BaiRongTerRequester extends BairongRequester {

  @Override protected Optional<String> matchModule(String module) {
    if (!"SpecialList_c".equals(module) && !"ApplyLoan".equals(module)) {
      return Optional.of("ter 现仅支持模块 SpecialList_c 和 ApplyLoan");
    }
    return Optional.absent();
  }

  @Override protected CreditResult doRequest(CreditResult creditResult, String[] module) {
    String json =
        bairongProxyServer.requestTerApiData(creditResult.getUserName(), creditResult.getMobile(),
            creditResult.getIdNumber(), creditResult.getBankCard(), module);
    creditResult.setResult(json);
    Map<String, Object> mapResult = parseCode(json);
    String code = objectToString(mapResult.get("code"));
    String responseId = objectToString(mapResult.get("swift_number"));
    creditResult.setCreditCode(code);
    creditResult.setResponseId(responseId);
    String riskCode = CreditResultCode.SYSTEM_ERROR;
    switch (code) {
      case "00":
        riskCode = CreditResultCode.TARGET_HIT;
        break;
      case "100002":
        riskCode = CreditResultCode.TARGET_MISSING;
        break;
      case "100007":
        throw new RiskCreditTokenExpiredException("评估报告API token过期");
    }
    creditResult.setRiskCode(riskCode);
    return creditResult;
  }
}
