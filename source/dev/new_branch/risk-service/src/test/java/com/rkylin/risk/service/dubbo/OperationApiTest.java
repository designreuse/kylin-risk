package com.rkylin.risk.service.dubbo;

import com.rkylin.risk.operation.api.OperationApi;
import com.rkylin.risk.operation.bean.CustomerMsg;
import com.rkylin.risk.operation.bean.ResultInfo;
import com.rkylin.risk.service.ApiServiceConstants;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lina on 2016-4-5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring/spring-dubbo-consumer-test.xml"})
public class OperationApiTest {

  @Resource
  private OperationApi operationApi;

  private static ClassPathXmlApplicationContext applicationContext;

  @BeforeClass
  public static void beforeClass() {
    applicationContext =
        new ClassPathXmlApplicationContext("classpath:/risk-spring-with-dubbo.xml");
  }

  @AfterClass
  public static void afterClass() {
    if (applicationContext != null) {
      applicationContext.close();
    }
  }

  @Test
  public void customerinfoNotNullTest() {
    CustomerMsg cust = new CustomerMsg();
    cust.setCustomerid("100004");
    cust.setCustomername("测试客户4");
    cust.setCertificateid("1234567894");
    cust.setAge("30");
    cust.setBurdenRate("2");
    cust.setCashDepositRate("0.1");
    cust.setComType("INTERPRISE");
    cust.setCustSource("COMPANY");
    cust.setEduDegree("HIGHSCHOOL");
    cust.setEntryTime("2");
    cust.setFinancingTime("3");
    cust.setGPS("no");
    cust.setHouseArea("TOWNSHIP");
    cust.setHouseRegion("一类地区");
    cust.setHouseSize("120");
    cust.setHouseType("COMMERCIAL");
    cust.setIllegalRecord("ADMPENALTY");
    cust.setLeftKey("");
    cust.setMarriage("MARRIEDANDCHILD");
    cust.setMaxOverdue("50");
    cust.setMortInfo("LIFTMORTGAGE");
    cust.setOtherEstate("");
    cust.setWage("20000");
    cust.setRegistArea("");
    cust.setResideTime("4");
    cust.setPosition("MANAGER");
    cust.setRentRate("3");
    cust.setOverdue("");
    cust.setOverdueRate("");
    ResultInfo resultInfo = operationApi.customerinfo(cust,
        getHmac(cust.getCustomerid(), cust.getCustomername(), cust.getCertificateid()));

    assertThat(resultInfo.getResultCode()).isEqualTo("0");
    assertThat(resultInfo.getResultMsg()).isEqualTo("0");
    assertThat(resultInfo.getResultScore()).isEqualTo("58.0");
  }

  public String getHmac(String customerid, String customername, String artid) {
    return DigestUtils.md5Hex(
        customerid + customername + artid + ApiServiceConstants.OPER_API_HMAC);
  }
}

