package com.rkylin.risk.service.api.impl;

import com.rkylin.risk.operation.api.OperationApi;
import com.rkylin.risk.operation.bean.CustomerMsg;
import com.rkylin.risk.operation.bean.ResultInfo;
import com.rkylin.risk.service.ApiServiceConstants;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lina on 2016-4-5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class OperationApiImplTest {

  @Resource
  private OperationApi operationApi;

  @Test
  public void customerinfoNotNullTest() {
    CustomerMsg cust = new CustomerMsg();
    cust.setCustomerid("1000010");
    cust.setCustomername("测试客户10");
    cust.setCertificateid("12345678910");
    cust.setAge("40");
    cust.setBurdenRate("2");
    cust.setCashDepositRate("0.1");
    cust.setComType("INTERPRISE");
    cust.setCustSource("COMPANY");
    cust.setEduDegree("HIGHSCHOOL");
    cust.setEntryTime("2");
    cust.setFinancingTime("3");
    cust.setGPS("false");
    cust.setHouseArea("TOWNSHIP");
    cust.setHouseRegion("first");
    cust.setHouseSize("120");
    cust.setHouseType("COMMERCIAL");
    cust.setIllegalRecord("ADMPENALTY");
    cust.setLeftKey("false");
    cust.setMarriage("MARRIEDANDCHILD");
    cust.setMaxOverdue("70");
    cust.setMortInfo("LIFTMORTGAGE");
    cust.setOtherEstate("10000");
    cust.setWage("20000");
    cust.setRegistArea("second");
    cust.setResideTime("4");
    cust.setPosition("MANAGER");
    cust.setRentRate("2");
    cust.setOverdue("6");
    cust.setOverdueRate("0.1");
    ResultInfo resultInfo = operationApi.customerinfo(cust,
        getHmac(cust.getCustomerid(), cust.getCustomername(), cust.getCertificateid()));

    assertThat(resultInfo.getResultCode()).isEqualTo("0");
    //assertThat(resultInfo.getResultMsg()).isEqualTo("0");
    //assertThat(resultInfo.getResultScore()).isEqualTo("58.0");
  }

  public String getHmac(String customerid, String customername, String artid) {
    return DigestUtils.md5Hex(
        customerid + customername + artid + ApiServiceConstants.OPER_API_HMAC);
  }
}

