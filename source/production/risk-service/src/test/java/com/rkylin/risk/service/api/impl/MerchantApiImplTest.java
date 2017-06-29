package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.order.api.MerchantApi;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.bean.MerchantFactor;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

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
    merchantFactor.setUserid("u0003");
    merchantFactor.setConstid("M00004");
    merchantFactor.setCompanyname("金红叶纸业有限责任公司");
    merchantFactor.setRegistrationorga("r12323456571");
    merchantFactor.setBusinesslicense("b13456783561");
    merchantFactor.setCertificatestartdate(LocalDate.parse("2010-08-16"));
    merchantFactor.setCertificateexpiredate(LocalDate.parse("2020-08-16", DateTimeFormat.forPattern("yyyy-MM-DD")));
    merchantFactor.setCompanytype("1");
    merchantFactor.setRegistfinance("1000000");
    merchantFactor.setAddress("广东省广州经济技术开发区");
    merchantFactor.setTaxregcard("t2876543232");
    merchantFactor.setOrgancertificate("o7389343854");
    merchantFactor.setAcuntopenlince("a548930232");
    merchantFactor.setCorporatename("we");
    merchantFactor.setCertificatetype("2");
    merchantFactor.setCertificatenumber("23958734958494");
    merchantFactor.setProvincenum(null);
    merchantFactor.setSubagencynum("100");
    merchantFactor.setTrainingnumyear("10000");
    merchantFactor.setTrainingincomeyear("1000000");
    merchantFactor.setFoundage("5");
    merchantFactor.setAreateaching("10000");
    merchantFactor.setBranchflag("1");
    merchantFactor.setClassprice("10000.23");
    merchantFactor.setCheckorderid("4757");
    merchantFactor.setClasslist("");
    String hmacString = DigestUtils.md5Hex(
        new StringBuilder(merchantFactor.getUserid()).append(merchantFactor.getConstid())
            .append(merchantFactor.getCompanyname())
            .append(merchantFactor.getCorporatename())
            .append(ApiServiceConstants.ORDER_API_HMAC)
            .toString());
    merchantFactor.setHmac(hmacString);
    try {
      String result = merchantApi.merchantmsg(objectMapper.writeValueAsString(merchantFactor));
      System.out.println(result);
      assertThat(result).isNotNull();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
