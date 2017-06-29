package com.rkylin.risk.mybatis.cache.client.jedis;

import com.rkylin.risk.mybatis.cache.client.RedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/4 version: 1.0
 */
public class JedisClient implements RedisClient {
  private static JedisPool pool;

  static {
    RedisConfig redisConfig = RedisConfigurationBuilder.getInstance().parseConfiguration();
    pool = new JedisPool(redisConfig, redisConfig.getHost(), redisConfig.getPort(),
        redisConfig.getConnectionTimeout(), redisConfig.getSoTimeout(), redisConfig.getPassword(),
        redisConfig.getDatabase(), redisConfig.getClientName());
  }

  private <T> T execute(RedisCallback<T> callback) {
    Jedis jedis = pool.getResource();
    try {
      return callback.doWithRedis(jedis);
    } finally {
      jedis.close();
    }
  }

  @Override
  public Object getObject(final String key, final Object hashKey) {
    return execute(new RedisCallback() {
      @Override
      public Object doWithRedis(Jedis jedis) {
        return SerializeUtil.unserialize(
            jedis.hget(key.toString().getBytes(), hashKey.toString().getBytes()));
      }
    });
  }

  @Override
  public void removeObject(final String key, final Object hashKey) {
    execute(new RedisCallback() {
      @Override
      public Object doWithRedis(Jedis jedis) {
        return jedis.hdel(key.toString().getBytes(), hashKey.toString().getBytes());
      }
    });
  }

  @Override
  public void putObject(final String key, final Object hashKey, final Object hashValue) {
    execute(new RedisCallback() {
      @Override
      public Object doWithRedis(Jedis jedis) {
        jedis.hset(key.toString().getBytes(), hashKey.toString().getBytes(),
            SerializeUtil.serialize(hashValue));
        return null;
      }
    });
  }

  @Override
  public void clear(final String key) {
    execute(new RedisCallback() {
      @Override
      public Object doWithRedis(Jedis jedis) {
        jedis.del(key.toString().getBytes());
        return null;
      }
    });
  }

  @Override
  public Long getSize(final String key) {
    return execute(new RedisCallback<Long>() {
      @Override
      public Long doWithRedis(Jedis jedis) {
        return jedis.hlen(key.toString().getBytes());
      }
    });
  }
}
