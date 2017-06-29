package com.rkylin.risk.mybatis.cache.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/5 version: 1.0
 */
public class RedisCachingManagerImpl implements RedisCachingManager {
  //每一个statementId 更新依赖的statementId集合
  private Map<String, Set<String>> observers = new ConcurrentHashMap<String, Set<String>>();

  //全局性的  statemntId与CacheKey集合
  private CacheKeysPool sharedCacheKeysPool = new CacheKeysPool();
  //记录每一个statementId 对应的Cache 对象
  private Map<String, Cache> holds = new ConcurrentHashMap<String, Cache>();
  private boolean initialized = false;

  private static RedisCachingManagerImpl enhancedCacheManager;

  private RedisCachingManagerImpl() {
  }

  public static RedisCachingManagerImpl getInstance() {
    return enhancedCacheManager == null ? (enhancedCacheManager = new RedisCachingManagerImpl())
        : enhancedCacheManager;
  }

  public void refreshCacheKey(CacheKeysPool keysPool) {
    sharedCacheKeysPool.putAll(keysPool);
    //sharedCacheKeysPool.display();
  }

  public void clearRelatedCaches(final Set<String> set) {
    //sharedCacheKeysPool.display();
    for (String observable : set) {
      Set<String> relatedStatements = observers.get(observable);
      for (String statementId : relatedStatements) {
        Cache cache = holds.get(statementId);
        Set<Object> cacheKeys = sharedCacheKeysPool.get(statementId);
        for (Object cacheKey : cacheKeys) {
          cache.removeObject(cacheKey);
        }
      }
      // clear shared cacheKey Pool width specific key
      sharedCacheKeysPool.remove(observable);
    }
  }

  public boolean isInitialized() {
    return initialized;
  }

  public void initialize(Properties properties) {
    String dependency = properties.getProperty("dependency");
    if (!("".equals(dependency) || dependency == null)) {
      InputStream inputStream = null;
      try {
        inputStream = Resources.getResourceAsStream(dependency);
        XPathParser parser = new XPathParser(inputStream);
        List<XNode> statements = parser.evalNodes("/caches/statements/statement");
        for (XNode node : statements) {
          Set<String> temp = new HashSet<String>();
          List<XNode> obs = node.evalNodes("observer");
          for (XNode observer : obs) {
            temp.add(observer.getStringAttribute("id"));
          }
          this.observers.put(node.getStringAttribute("id"), temp);
        }
        initialized = true;
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (inputStream != null) {
          try {
            inputStream.close();
          } catch (IOException e) {
          }
        }
      }
    }
  }

  public void appendStatementCacheMap(String statementId, Cache cache) {
    if (holds.containsKey(statementId) && holds.get(statementId) != null) {
      return;
    }
    holds.put(statementId, cache);
  }
}
