package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.BaseRedisDao;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/16 version: 1.0
 */
public class BaseRedisDaoImpl<K, V> implements BaseRedisDao<K, V> {
  @Setter
  private int defaultTimeout = 3 * 60;
  @Setter
  private RedisTemplate<K, V> redisTemplate;

  @Override
  public void set(K key, V value, long timeout, TimeUnit unit) {
    redisTemplate.opsForValue().set(key, value, timeout, unit);
  }

  @Override
  public void set(K key, V value, int sencods) {
    redisTemplate.opsForValue().set(key, value, sencods, TimeUnit.SECONDS);
  }

  @Override
  public void set(K key, V value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public void setWithTimeout(K key, V value) {
    redisTemplate.opsForValue().set(key, value, defaultTimeout, TimeUnit.SECONDS);
  }

  @Override
  public V get(K key) {
    return redisTemplate.opsForValue().get(key);
  }
}
