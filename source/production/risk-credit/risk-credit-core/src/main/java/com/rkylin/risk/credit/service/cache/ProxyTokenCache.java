package com.rkylin.risk.credit.service.cache;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tomalloc on 16-7-29.
 */
public class ProxyTokenCache<K, V> implements TokenCache<K, V> {
  private List<TokenCache<K, V>> tokenCacheList = new LinkedList<>();

  private static ProxyTokenCache cache;

  @Override public void set(K k, V v) {
    for (TokenCache cache : tokenCacheList) {
      cache.set(k, v);
    }
  }

  @Override public V get(K k) {
    for (TokenCache<K, V> cache : tokenCacheList) {
      V v = cache.get(k);
      if (v != null) {
        return v;
      }
    }
    return null;
  }

  public void addCache(TokenCache cache) {
    tokenCacheList.add(cache);
  }

  public static ProxyTokenCache cache() {
    if (cache == null) {
      synchronized (ProxyTokenCache.class) {
        if (cache == null) {
          cache = new ProxyTokenCache();
        }
      }
    }
    return cache;
  }
}
