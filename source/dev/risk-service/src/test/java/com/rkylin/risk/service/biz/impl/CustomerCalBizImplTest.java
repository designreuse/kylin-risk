package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.dto.ScoreRuleBean;
import com.rkylin.risk.service.biz.CustomerCalBiz;
import java.io.IOException;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lina on 2016-6-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class CustomerCalBizImplTest {

  @Resource
  private CustomerCalBiz customerCalBiz;

  @Test
  public void calCustomerFactorTest() throws IOException {
    ScoreRuleBean cust = new ScoreRuleBean();

    cust.setAge(new Amount(40));
    cust.setBurdenRate(new Amount(2));
    cust.setCashDepositRate(new Amount(0.1));
    cust.setComType("INTERPRISE");
    cust.setCustSource("COMPANY");
    cust.setEduDegree("HIGHSCHOOL");
    cust.setEntryTime(new Amount(2));
    cust.setFinancingTime(new Amount(3));
    cust.setGps("no");
    cust.setHouseArea("TOWNSHIP");
    cust.setHouseRegion("一类地区");
    cust.setHouseSize(new Amount(120));
    cust.setHouseType("COMMERCIAL");
    cust.setIllegalRecord("ADMPENALTY");
    cust.setLeftKey("");
    cust.setMarriage("MARRIEDANDCHILD");
    cust.setMaxOverdue(new Amount(50));
    cust.setMortInfo("LIFTMORTGAGE");
    cust.setOtherEstate(new Amount());
    cust.setWage(new Amount(50000));
    cust.setRegistArea("");
    cust.setResideTime(new Amount(4));
    cust.setPosition("MANAGER");
    cust.setRentRate(new Amount(2));
    cust.setOverdue(null);
    cust.setOverdueRate(null);
    ResultBean resultBean = customerCalBiz.calCustomerFactor(cust);
    System.out.println(resultBean);
  }
}
