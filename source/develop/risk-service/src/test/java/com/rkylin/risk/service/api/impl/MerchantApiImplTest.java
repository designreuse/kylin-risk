package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.order.api.MerchantApi;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.bean.MerchantFactor;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lina on 2016-8-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class MerchantApiImplTest {

  @Resource
  private MerchantApi merchantApi;
  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void merchantmsgTest() {
    MerchantFactor merchantFactor = new MerchantFactor();
    merchantFactor.setUserid("u00067");
    merchantFactor.setConstid("M00004");
    merchantFactor.setCompanyname("清风纸业");
    merchantFactor.setRegistrationorga("r12323456571");
    merchantFactor.setBusinesslicense("b13456783561");
    merchantFactor.setCertificatestartdate("2010-08-16 00:00:00");
    merchantFactor.setCertificateexpiredate("2020-08-16 00:00:00");
    merchantFactor.setCompanytype("1");
    merchantFactor.setRegistfinance("1000000");
    merchantFactor.setAddress("广东省广州经济技术开发区");
    merchantFactor.setTaxregcard("t2876543232");
    merchantFactor.setOrgancertificate("o7389343854");
    merchantFactor.setAcuntopenlince("a548930232");
    merchantFactor.setCorporatename("we");
    merchantFactor.setCertificatetype("2");
    merchantFactor.setCertificatenumber("23958734958494");
    merchantFactor.setProvincenum("50");
    merchantFactor.setSubagencynum("100");
    merchantFactor.setTrainingnumyear("10000");
    merchantFactor.setTrainingincomeyear("1000000");
    merchantFactor.setFoundage("5");
    merchantFactor.setAreateaching("10000");
    merchantFactor.setBranchflag("1");
    merchantFactor.setClassprice("10000.23");
    merchantFactor.setCheckorderid("47574");
    String hmacString = DigestUtils.md5Hex(
        new StringBuilder(merchantFactor.getUserid()).append(merchantFactor.getConstid())
            .append(merchantFactor.getCompanyname())
            .append(merchantFactor.getCorporatename())
            .append(ApiServiceConstants.ORDER_API_HMAC)
            .toString());
    merchantFactor.setHmac(hmacString);

    try {
      String result = merchantApi.merchantmsg("");
      System.out.println(result);
      //assertThat(result).isNotNull();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
