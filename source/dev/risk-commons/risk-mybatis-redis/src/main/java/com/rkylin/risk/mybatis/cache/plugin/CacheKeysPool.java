package com.rkylin.risk.mybatis.cache.plugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/5 version: 1.0
 */
public class CacheKeysPool {
  private Map<String, Set<Object>> pool = new ConcurrentHashMap<String, Set<Object>>();

  public Set<Object> get(String key) {
    if (pool.get(key) == null) {
      pool.put(key, new HashSet<Object>());
    }
    return pool.get(key);
  }

  public Set<Object> put(String key, Set<Object> value) {
    return pool.put(key, value);
  }

  public void putElement(String key, Object element) {
    if (pool.get(key) == null) {
      pool.put(key, new HashSet<Object>());
    }
    pool.get(key).add(element);
  }

  public Set<Object> remove(String key) {
    return pool.remove(key);
  }

  public void clear() {
    pool.clear();
  }

  public Set<String> keySet() {
    return pool.keySet();
  }

  public Map<String, Set<Object>> getOriginalPool() {
    return pool;
  }

  public Set<Map.Entry<String, Set<Object>>> entrySet() {
    return this.pool.entrySet();
  }

  public void putAll(CacheKeysPool pool) {
    for (Map.Entry<String, Set<Object>> entry : pool.entrySet()) {
      for (Object item : entry.getValue()) {
        this.putElement(entry.getKey(), item);
      }
    }
  }
}
