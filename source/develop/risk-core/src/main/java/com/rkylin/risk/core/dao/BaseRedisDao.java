package com.rkylin.risk.core.dao;

import java.util.concurrent.TimeUnit;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/16 version: 1.0
 */
public interface BaseRedisDao<K, V> {
  void set(K key, V value, long timeout, TimeUnit unit);

  void set(K key, V value, int sencods);

  void set(K key, V value);

  void setWithTimeout(K key, V value);

  V get(K key);
}
