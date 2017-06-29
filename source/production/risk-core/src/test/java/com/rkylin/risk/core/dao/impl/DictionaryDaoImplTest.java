package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.BaseTest;
import com.rkylin.risk.core.dao.DictionaryDao;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-7-11.
 */
public class DictionaryDaoImplTest extends BaseTest {
  @Resource
  private DictionaryDao dictionaryDao;



  @Test
  public void queryCodeAndNameTest() {
    Map<String, String> map = dictionaryDao.queryCodeAndName("status");
    System.out.println("map:" + map);
    assertThat(map).isNotNull();
    assertThat(map).isNotEmpty();
  }
}
