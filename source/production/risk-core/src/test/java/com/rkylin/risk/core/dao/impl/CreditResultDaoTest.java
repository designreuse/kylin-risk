package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CreditResultDao;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.enumtype.CreditProductType;
import java.util.List;
import javax.annotation.Resource;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-12-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring-test.xml"})
public class CreditResultDaoTest {

  @Resource
  private CreditResultDao creditResultDao;

  @Test
  public void queryUnionPayCreditResultTest() {
    List<CreditResult> creditResults =
        creditResultDao.queryUnionPayCreditResult("6222024000073008605");
    assertThat(creditResults).isNotEmpty();
  }

  @Test
  public void insertTest() {
    CreditResult creditResult = new CreditResult();
    creditResult.setRequestTime(new DateTime());
    creditResult.setCreditModuleId(1l);
    creditResult.setCreditProduct(CreditProductType.BAIRONG.name());
    creditResultDao.insert(creditResult);
  }

  @Test
  public void queryCommonCreditResultTest() {
    creditResultDao.queryCommonCreditResult("1111", CreditProductType.PY, 1l);
  }
}
