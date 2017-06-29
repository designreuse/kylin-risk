package com.rkylin.risk.credit.service.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tomalloc on 16-7-29.
 */
public class LocalTokenCache<K, V> implements TokenCache<K, V> {
  private ConcurrentHashMap<K, V> hashMap = new ConcurrentHashMap<K, V>();

  @Override public void set(K k, V v) {
    hashMap.put(k, v);
  }

  @Override public V get(K k) {
    return hashMap.get(k);
  }
}
