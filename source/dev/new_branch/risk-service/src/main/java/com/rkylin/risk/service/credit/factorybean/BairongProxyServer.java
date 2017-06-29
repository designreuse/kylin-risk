package com.rkylin.risk.service.credit.factorybean;

import com.bfd.facade.MerchantServer;
import com.br.bean.DasBean;
import com.br.bean.MerchantBean;
import com.br.bean.TerBean;
import java.util.concurrent.TimeUnit;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by tomalloc on 16-12-13.
 */
@Slf4j
public class BairongProxyServer {

  private String bairongUserName;

  private String bairongPassword;

  private MerchantServer merchantServer;

  @Setter
  private RedisTemplate redisTemplate;

  /**
   *
   * 缓存token
   * token应存放到集中式缓存中
   */
  private volatile static String cacheToken;

  private static final String TOKEN_ID="$risk_credit_bairong_token";

  public BairongProxyServer(String bairongUserName, String bairongPassword) {
    this.bairongUserName = bairongUserName;
    this.bairongPassword = bairongPassword;
    this.merchantServer = new MerchantServer();
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

  private String loadToken(){
    //登陆
    int i = 0;
    while (i < 3) {
      //登陆失败,尝试重试3次
      try {
        String loginResult = merchantServer.login(bairongUserName, bairongPassword);
        if (StringUtils.isNotBlank(loginResult)) {
          JSONObject json = JSONObject.fromObject(loginResult);
          String code = json.getString("code");
          String token = json.getString("tokenid");
          if ("00".equals(code)) {
            redisTemplate.opsForValue().set(TOKEN_ID,token);
            return token;
          }
          log.info("bairong login fail,result:{}", loginResult);
        }
        try {
          TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {

        }
        i++;
      } catch (Exception e) {
        log.error("百融登陆异常", e);
        throw new RuntimeException("百融登陆异常", e);
      }
    }
    throw new RuntimeException("token获取失败");
  }
  /**
   * 刷新token,强制刷新token
   */
  public String refreshToken(boolean force) {
    if (!force) {
      if (cacheToken != null) {
        return cacheToken;
      }
      Object redisToken=  redisTemplate.opsForValue().get(TOKEN_ID);
      if(redisToken!=null){
        cacheToken=redisToken.toString();
        return cacheToken;
      }
      cacheToken=loadToken();
      return cacheToken;
    }

    //如果本地的token和redis的token不一致，从redis获取
    Object redisToken=  redisTemplate.opsForValue().get(TOKEN_ID);
    if(redisToken!=null&&!redisToken.equals(cacheToken)){
      cacheToken=redisToken.toString();
      return cacheToken;
    }
    cacheToken=loadToken();
    return cacheToken;
  }

  public String requestDasApiData(String name, String mobile, String idNumber, String bankCard,
      String[] moduleList) {
    DasBean dasBean = new DasBean();
    dasBean.setApiType(BrApiType.DAS.toString());
    dasBean.setId(idNumber);
    dasBean.setName(name);
    dasBean.setCell(mobile);
    dasBean.setBank_id(bankCard);
    //设置套餐
    dasBean.setMeal(StringUtils.join(moduleList,","));
    return request(dasBean);
  }

  public String request(MerchantBean merchantBean) {
    merchantBean.setTokenid(refreshToken(false));
    //TODO 失败处理
    try {
      String data = merchantServer.getApiData(merchantBean);

      return data;
    } catch (Exception e) {
      throw new RuntimeException("百融查询出错", e);
    }
  }

  public String requestTerApiData(String name, String mobile, String idNumber, String bankCard,
      String[] moduleList) {
    TerBean terBean = new TerBean();
    terBean.setApiType(BrApiType.TER.toString());
    terBean.setGid("");
    terBean.setId(idNumber);
    terBean.setName(name);
    terBean.setCell(mobile);
    terBean.setBank_id(bankCard);
    //设置套餐
    terBean.setMeal(StringUtils.join(moduleList,","));
    return request(terBean);
  }
}
