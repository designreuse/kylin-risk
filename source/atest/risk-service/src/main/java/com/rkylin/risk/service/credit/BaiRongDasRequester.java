package com.rkylin.risk.service.credit;

import com.google.common.base.Optional;
import com.rkylin.risk.core.entity.CreditResult;
import java.util.Map;

/**
 * 百融Das api Created by tomalloc on 16-12-13.
 */
public class BaiRongDasRequester extends BairongRequester {

  @Override protected Optional<String> matchModule(String module) {
    if (!"EduLevel".equals(module)) {
      return Optional.of("das 现仅支持模块 EduLevel");
    }
    return Optional.absent();
  }

  @Override protected CreditResult doRequest(CreditResult creditResult, String[] module) {
    String json =
        bairongProxyServer.requestDasApiData(creditResult.getUserName(), creditResult.getMobile(),
            creditResult.getIdNumber(), creditResult.getBankCard(), module);
    creditResult.setResult(json);
    Map<String, Object> mapResult = parseCode(json);
    String code = objectToString(mapResult.get("code"));
    String responseId = objectToString(mapResult.get("swift_number"));
    creditResult.setCreditCode(code);
    creditResult.setResponseId(responseId);
    //TODO code 处理
    String riskCode = "99999";
    switch (code) {
      case "600000":
        riskCode = "10000";
        break;
      case "100007":
        throw new RiskCreditTokenExpiredException("百融海纳API token过期");
    }

    creditResult.setRiskCode(riskCode);
    return creditResult;
  }
}
