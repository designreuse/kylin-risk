package com.rkylin.risk.credit.service.cache;

/**
 * Created by tomalloc on 16-7-29.
 */
public interface TokenCache<K, V> {
  /**
   * 设置缓存
   * @param k
   * @param v
   */
  void set(K k, V v);


  /**
   * 取得缓存
   * @param k
   * @return
   */
  V get(K k);
}
