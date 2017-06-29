package com.rkylin.risk.mybatis.cache.client.spring;

import com.rkylin.risk.mybatis.cache.client.RedisClient;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/3 version: 1.0
 */
public class SpringRedisClient implements RedisClient, InitializingBean {
  @Setter
  private RedisTemplate redisTemplate;

  @Override
  public Object getObject(final String key, final Object hashKey) {
    return redisTemplate.opsForHash().get(key, hashKey);
  }

  @Override
  public void removeObject(String key, Object hashKey) {
    redisTemplate.opsForHash().delete(key, hashKey);
  }

  @Override
  public void putObject(String key, Object hashKey, Object hashValue) {
    redisTemplate.opsForHash().put(key, hashKey, hashValue);
  }

  @Override
  public void clear(String key) {
    redisTemplate.delete(key);
  }

  @Override
  public Long getSize(String key) {
    return redisTemplate.opsForHash().size(key);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (redisTemplate == null) {
      throw new NullPointerException();
    }
  }
}
