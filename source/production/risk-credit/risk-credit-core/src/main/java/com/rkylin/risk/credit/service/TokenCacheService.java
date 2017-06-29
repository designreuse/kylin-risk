package com.rkylin.risk.credit.service;

import com.rkylin.risk.credit.service.cache.ProxyTokenCache;

/**
 * Created by tomalloc on 16-8-2.
 */
interface TokenCacheService {
  void setCache(ProxyTokenCache cache);
}
