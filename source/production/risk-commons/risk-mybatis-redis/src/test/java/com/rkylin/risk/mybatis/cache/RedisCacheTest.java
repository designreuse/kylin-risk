package com.rkylin.risk.mybatis.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/11/5 version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-redis.xml")
public class RedisCacheTest extends AbstractJUnit4SpringContextTests {
  private RedisCache redisCache;

  @Before
  public void before() {
    redisCache = new RedisCache("RISK");
  }

  @Test
  public void getObjectTest() {
    for (int i = 0; i < 10; i++) {
      redisCache.putObject(i, i + 1);
    }
    for (int i = 0; i < 10; i++) {
      assertThat(redisCache.getObject(i)).isEqualTo(i + 1);
    }
    assertThat(redisCache.getSize()).isEqualTo(10);
  }

  @Test
  public void clearTest() {
    Date date = new Date();
    redisCache.putObject(1, new Date());
    redisCache.clear();
    assertThat(redisCache.getObject(1)).isNull();
  }
}
