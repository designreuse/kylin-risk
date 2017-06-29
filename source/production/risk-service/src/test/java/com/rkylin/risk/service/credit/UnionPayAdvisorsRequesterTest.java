package com.rkylin.risk.service.credit;

import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.service.bean.CreditRequestEntity;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-11-30.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring/risk-db-test.xml",
    "classpath:/risk-spring/spring-credit-test.xml", "classpath:/risk-spring/spring-mail.xml"})
public class UnionPayAdvisorsRequesterTest {

  @Resource
  private UnionPayAdvisorsRequester unionPayAdvisorsRequester;

  @Test
  public void requestTest() {
    CreditRequestEntity creditRequestEntity = new CreditRequestEntity();
    creditRequestEntity.setBankCard("6212264000066141538");
    creditRequestEntity.setOrderId("12");
    creditRequestEntity.setWorkflowId("jjk");
    CreditResult creditResult =
        unionPayAdvisorsRequester.requestCreditResult(creditRequestEntity, null);
    assertThat(creditResult).isNotNull();
    assertThat(creditResult.getCreditCode()).isEqualTo("10101");
  }
}
