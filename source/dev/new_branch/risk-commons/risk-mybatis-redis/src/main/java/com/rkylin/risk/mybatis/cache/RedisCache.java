/**
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.rkylin.risk.mybatis.cache;

import com.rkylin.risk.mybatis.cache.client.RedisClient;
import com.rkylin.risk.mybatis.cache.client.jedis.JedisClient;
import com.rkylin.risk.mybatis.cache.client.spring.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Cache adapter for Redis.
 *
 * @author Eduardo Macarron
 */
@Slf4j
public final class RedisCache implements Cache {

  private final ReadWriteLock readWriteLock = new DummyReadWriteLock();

  private String id;

  private RedisClient redisClient;

  public RedisCache(final String id) {
    if (id == null) {
      throw new IllegalArgumentException("Cache instances require an ID");
    }
    this.id = id;
    try {
      redisClient = SpringContextUtils.getBean("redisClient");
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    if (redisClient == null) {
      redisClient = new JedisClient();
    }
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public int getSize() {
    long size = redisClient.getSize(id);
    return (int) size;
  }

  @Override
  public void putObject(final Object key, final Object value) {
    redisClient.putObject(id, key, value);
  }

  @Override
  public Object getObject(final Object key) {
    return redisClient.getObject(id, key);
  }

  @Override
  public Object removeObject(final Object key) {
    redisClient.removeObject(id, key);
    return null;
  }

  @Override
  public void clear() {
    redisClient.clear(id);
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return readWriteLock;
  }

  @Override
  public String toString() {
    return "Redis {" + id + "}";
  }
}
