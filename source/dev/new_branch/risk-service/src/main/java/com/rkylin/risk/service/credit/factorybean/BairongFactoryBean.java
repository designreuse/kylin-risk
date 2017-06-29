package com.rkylin.risk.service.credit.factorybean;

import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by tomalloc on 16-12-13.
 */
public class BairongFactoryBean implements FactoryBean<BairongProxyServer>,InitializingBean {

  @Setter
  private String bairongUserName;

  @Setter
  private String bairongPassword;

  @Setter
  private RedisTemplate redisTemplate;

  private BairongProxyServer bairongProxyServer;
  @Override public BairongProxyServer getObject() throws Exception {
    return bairongProxyServer;
  }

  @Override public Class<?> getObjectType() {
    return BairongProxyServer.class;
  }

  @Override public boolean isSingleton() {
    return true;
  }

  @Override public void afterPropertiesSet() throws Exception {
    bairongProxyServer=new BairongProxyServer(bairongUserName,bairongPassword);
    if(redisTemplate!=null){
      bairongProxyServer.setRedisTemplate(redisTemplate);
    }
  }
}
