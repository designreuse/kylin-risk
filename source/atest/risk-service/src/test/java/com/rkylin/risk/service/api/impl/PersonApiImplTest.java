package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.operation.api.PersonApi;
import com.rkylin.risk.operation.bean.PersonMsg;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.bean.PersonFactor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cuixiaofang on 2016-7-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class PersonApiImplTest {
  @Resource
  private PersonApi personApi;
  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void isPassPersonmsgTest() throws JsonProcessingException {
    PersonFactor personFactor = new PersonFactor();
    //911606774409171136
    personFactor.setUserid("576776");
    personFactor.setUserrelatedid("991607963239232110");
    personFactor.setConstid("M000004");
    personFactor.setUsername("何锋洲");
    personFactor.setCertificatenumber("622827198411293133");

    personFactor.setMobilephone("15600220022");
    personFactor.setAge("29");
    personFactor.setEducation("4");
    personFactor.setCheckorderid("4623785");
    personFactor.setClassname("学历自考专升本课程");
    personFactor.setClassprice("5000");
    personFactor.setTcaccount("6228480018676705371");

    personFactor.setCourseId("1003506");
    personFactor.setCorporationId("11914");
    personFactor.setCorporationname("融数大学");
    personFactor.setCouserStairClassify("课程一级分类");
    personFactor.setCourseSecondaryClassify("课程二级分类");

    personFactor.setNation("汉族");
    personFactor.setAddress("甘肃省镇原县马渠乡牡丹洼行政村何老庄自然村41号");
    personFactor.setCertificatestartdate("2013-02-21");
    personFactor.setCertificateexpiredate("2033-02-21");
    personFactor.setQqnum("474569443");
    personFactor.setFirstman("qwe");
    personFactor.setFirstmobile("12345678914");
    personFactor.setFirstrelation("朋友");
    personFactor.setSecondman("asd");
    personFactor.setSecondmobile("13625845623");
    personFactor.setSecondrelation("同学");
    personFactor.setTaccountbank("中国农业银行");
    personFactor.setTaccountbranch("酒仙桥支行");
    personFactor.setTaccountprovince("北京");
    personFactor.setTaccountcity("北京");
    personFactor.setIsstudent("0");
    personFactor.setWorkCompanyName("alipay");
    personFactor.setMobilelist("156564843");

    String hmacString = new StringBuilder(personFactor.getUserid())
        .append(personFactor.getConstid())
        .append(personFactor.getUsername())
        .append(personFactor.getCertificatenumber())
        .append(ApiServiceConstants.ORDER_API_HMAC)
        .toString();
    personFactor.setHmac(DigestUtils.md5Hex(hmacString));
    //ba05b8d1-527d-4c20-b87f-999bad9e4ef2  kezhan
    //d7e4902a-ec60-43e5-b365-c8b40bba29d2
    //e83e9187-b150-439b-beaf-b2c3deb0622e
    //44d3a8db-ff81-4149-9767-6ff74870f1b6   kezhan
    //f2e5cd69-0773-49de-8e8f-6073e2454c75   kezhan

    //ab3c31dc-8b0b-4b35-bfa8-e6e386ad7d43
    //b4cfb653-990a-4d05-be21-0af728611162|df65e579-40d1-4502-a228-b51b1c830bc7|81fef6a8-c50f-4b0f-9d64-c8025788082c
    //"22d30933-9102-489a-a50d-5f1a5b178d18|3763ed5d-6efc-418f-8e5f-a3aa71169749|338fcb06-ee3e-4695-9bf0-a10d05d35e2e"
    personFactor.setUrlkey(
        "701e7298-3aed-43ca-b908-ef502496852b");
    String jsonStr = objectMapper.writeValueAsString(personFactor);
    System.out.println("参数----" + jsonStr);
    PersonMsg personMsg = personApi.personmsg(jsonStr);
    assertThat(personMsg).isNotNull();
    assertThat(personMsg.getResultCode()).isEqualTo("0");
    try {
      TimeUnit.MINUTES.sleep(20);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
