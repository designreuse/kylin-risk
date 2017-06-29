package com.rkylin.risk.mybatis.cache.client;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/3
 * version: 1.0
 */
public interface RedisClient {

    Object getObject(final String key, final Object hashKey);

    void removeObject(final String key, final Object hashKey);

    void putObject(final String key, final Object hashKey, final Object hashValue);

    void clear(final String key);

    Long getSize(final String key);
}
