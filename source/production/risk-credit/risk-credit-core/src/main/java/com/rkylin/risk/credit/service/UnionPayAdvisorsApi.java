package com.rkylin.risk.credit.service;

import com.rkylin.risk.commons.entity.ExternalCreditResult;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import com.rkylin.risk.credit.service.report.ChildReportProducer;
import com.rkylin.risk.credit.service.report.UninonPayAdvisorsChildReportProducer;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import upa.client.UPAClient;

/**
 * Created by tomalloc on 16-7-28.
 */
@Slf4j
public class UnionPayAdvisorsApi extends AbstractCreditProductApi<JSONObject>
    implements InitializingBean {

  @Value("${credit.unionpay.publicKey}")
  private String publicKey;
  @Value("${credit.unionpay.privateKey}")
  private String privateKey;
  @Value("${credit.unionpay.developmentId}")
  private String developmentId;
  @Value("${credit.unionpay.resourceId}")
  private String resourceId;
  @Value("${credit.unionpay.debugMode}")
  private String debugMode;
  @Value("${credit.unionpay.locationApi}")
  private String locationApi;

  private UPAClient upaClient;

  @Override protected ExternalCreditResult queryFromDataBase(CreditRequestParam requestParam) {
    return getCreditResultPersistent().queryUnionPayCreditResult(requestParam.getBankCard(),
        CreditProductType.UNIONPAY, module());
  }

  @Override public JSONObject doRequest(CreditRequestParam requestParam) {
    return upaClient.getCommonScoresByAccountNo(requestParam.getBankCard(), resourceId);
    /*
    int i = 0;
    String result = null;
    while (i < 3) {
      JSONObject jsonResult =
          upaClient.getCommonScoresByAccountNo(requestParam.getBankCard(), resourceId);
      String code = jsonResult.getString("error_code");
      //TODO 异常是否收费
      if ("0".equals(code)) {
        return jsonResult;
      } else if ("10102".equals(code) || "10101".equals(code)) {
        result = jsonResult.toString();
        try {
          TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        }
        i++;
        continue;
      }
      throw new RiskCreditException("银联智策错误代码:" + jsonResult);
    }
    throw new RiskCreditException("银联智策连接服务器错误:" + result);
    */
  }

  @Override protected ChildReportProducer childReportProducer(JSONObject data) {
    return new UninonPayAdvisorsChildReportProducer(data);
  }

  @Override protected CreditProductType productType() {
    return CreditProductType.UNIONPAY;
  }

  @Override protected String module() {
    return "ALL";
  }

  @Override public void afterPropertiesSet() throws Exception {
    upaClient = new UPAClient();
    // 设置开发者的ID
    upaClient.setDevelopmentId(developmentId);
    // 设置私钥
    upaClient.setPrivateKey(privateKey);
    // 设置公钥
    upaClient.setPublicKey(publicKey);
    // 设置是否调试模式，true：调试，默认是false
    upaClient.setDebugMode(Boolean.valueOf(debugMode));
    //设置访问的url地址,URL为我司给出的String类型字符串
    upaClient.setApiLocation(locationApi);
  }
}
