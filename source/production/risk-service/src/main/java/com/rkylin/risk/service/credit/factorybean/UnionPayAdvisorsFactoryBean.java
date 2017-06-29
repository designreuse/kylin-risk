package com.rkylin.risk.service.credit.factorybean;

import java.util.Objects;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import upa.client.UPAClient;

/**
 * Created by tomalloc on 16-11-30.
 */
public class UnionPayAdvisorsFactoryBean implements FactoryBean<UPAClient>,InitializingBean{
  @Setter
  private String publicKey;

  @Setter
  private String privateKey;

  @Setter
  private String developmentId;


  @Setter
  private boolean debugMode;

  @Setter
  private String locationApi;

  private UPAClient upaClient;

  @Override public UPAClient getObject() throws Exception {
    return upaClient;
  }

  @Override public Class<?> getObjectType() {
    return UPAClient.class;
  }

  @Override public boolean isSingleton() {
    return true;
  }

  @Override public void afterPropertiesSet() throws Exception {
    Objects.requireNonNull(developmentId,"银联智策开发者ID不能为空");
    Objects.requireNonNull(privateKey,"银联智策私钥不能为空");
    Objects.requireNonNull(publicKey,"银联智策公钥不能为空");
    Objects.requireNonNull(locationApi,"银联智策访问地址不能为空");


    upaClient=new UPAClient();
    // 设置开发者的ID
    upaClient.setDevelopmentId(developmentId);
    // 设置私钥
    upaClient.setPrivateKey(privateKey);
    // 设置公钥
    upaClient.setPublicKey(publicKey);
    // 设置是否调试模式，true：调试，默认是false
    upaClient.setDebugMode(debugMode);
    //设置访问的url地址,URL为我司给出的String类型字符串
    upaClient.setApiLocation(locationApi);
  }
}
