package com.rkylin.risk.credit.service;

import com.bfd.facade.MerchantServer;
import com.br.bean.MerchantBean;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import com.rkylin.risk.credit.exception.RiskCreditException;
import com.rkylin.risk.credit.service.cache.LocalTokenCache;
import com.rkylin.risk.credit.service.cache.ProxyTokenCache;
import com.rkylin.risk.credit.service.cache.TokenCache;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * 百融查询 Created by tomalloc on 16-7-28.
 */
@Slf4j
public abstract class BaiRongApi extends DefaultCreditProductApi<String>
    implements TokenCacheService, InitializingBean {

  /**
   * 用到的百融模块
   */
  @Setter
  private List<String> useModule;

  @Value("${credit.bairong.userName}")
  private String bairongUserName;

  @Value("${credit.bairong.password}")
  private String bairongPassword;

  private TokenCache cache;
  /**
   * 百融套餐
   */
  private String meal;
  private volatile String bariRongToken;

  private MerchantServer bairongMerchantServer = new MerchantServer();

  protected abstract MerchantBean wrapperRequestBean(String tokenId,
      CreditRequestParam requestParam);

  /**
   * 刷新token,强制刷新token
   */
  private String refreshToken(boolean force) {
    log.info("bairong login");
    if (!force) {
      Object token = cache.get(CreditConstant.BAIRONG_CACHE_TOKEN);
      if (token != null) {
        return token.toString();
      }
    }
    //登陆
    int i = 0;
    while (i < 3) {
      //登陆失败,尝试重试3次
      try {
        String loginResult = bairongMerchantServer.login(bairongUserName, bairongPassword);
        if (StringUtils.isNotBlank(loginResult)) {
          JSONObject json = JSONObject.fromObject(loginResult);
          String code = json.getString("code");
          String token = json.getString("tokenid");
          if ("00".equals(code)) {
            cache.set(CreditConstant.BAIRONG_CACHE_TOKEN, token);
            return token;
          }
          log.info("bairong login,result:{}", loginResult);
        }
        try {
          TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {

        }
        i++;
      } catch (Exception e) {
        log.error("百融登陆异常", e);
        throw new RiskCreditException("百融登陆异常", e);
      }
    }
    throw new RiskCreditException("token获取失败");
  }

  private String queryResult(CreditRequestParam requestParam) {
    MerchantBean merchantBean = wrapperRequestBean(bariRongToken, requestParam);
    try {
      return bairongMerchantServer.getApiData(merchantBean);
    } catch (Exception e) {
      throw new RiskCreditException("百融查询出错", e);
    }
  }

  @Override public String doRequest(CreditRequestParam requestParam) {
    synchronized (this) {
      if (bariRongToken == null) {
        bariRongToken = refreshToken(false);
      }
    }

    return queryResult(requestParam);
    /*
    int i = 0;
    while (i < 2) {
      try {
        return queryResult(requestParam);
      } catch (RiskCreditTokenExpireException e) {
        i++;
        bariRongToken = refreshToken(true);
      }
    }
    //未知的错误导致刷新token之后,仍然token过期
    throw new RiskCreditTokenExpireException("重新刷新token后,依旧token过期");
    */
  }

  @Override protected CreditProductType productType() {
    return CreditProductType.BAIRONG;
  }

  @Override protected String module() {
    return this.meal;
  }

  public abstract List<String> getDefaultModule();

  protected String getModule() {
    return this.meal;
  }

  @Override public void afterPropertiesSet() throws Exception {
    if (useModule == null || useModule.isEmpty()) {
      useModule = getDefaultModule();
    }
    StringBuilder mealBuilder = new StringBuilder(useModule.get(0));
    for (int i = 1; i < useModule.size(); i++) {
      mealBuilder.append(",");
      mealBuilder.append(useModule.get(i));
    }
    this.meal = mealBuilder.toString();

    if (cache == null) {
      cache = new LocalTokenCache();
    }
  }

  @Override public void setCache(ProxyTokenCache cache) {
    this.cache = cache;
  }

  protected enum BrApiType {
    TER("ter"),
    DAS("das");
    private String type;

    BrApiType(String type) {
      this.type = type;
    }

    @Override public String toString() {
      return this.type;
    }
  }
}
