package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.service.bean.PersonFactor;
import java.io.IOException;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Java6Assertions.fail;

/**
 * @author qiuxian
 * @create 2016-11-15 18:07
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring/spring-jackson-test.xml"})
public class JacksonMapperTest {

  @Resource
  private ObjectMapper jsonMapper;
  @Test
  public void toBeanTest() {

    String json="{\"corporationname\":\"北大青鸟\",\"firstrelation\":\"夫妻\",\"secondmobile\":\"13991060959\",\"certificatestartdate\":\"2006-03-01\",\"userrelatedid\":\"YSJ298160000001\",\"userid\":\"o83-VtzNAAn9wqg2MDTMmRrt55WQ\",\"courseSecondaryClassify\":\"数学1\",\"courseId\":\"2\",\"education\":\"5\",\"taccountbranch\":\"三元桥支行\",\"taccountprovince\":\"北京\",\"checkorderid\":\"8250\",\"mobilephone\":\"18610010001\",\"constid\":\"M000025\",\"username\":\"袁荣丽\",\"isauthor\":\"\",\"age\":\"-1\",\"classprice\":\"15200\",\"mobilelist\":\"\",\"creditcard\":\"621448548563\",\"certificatenumber\":\"10011001100111\",\"jdnum\":\"456京东号\",\"secondrelation\":\"父女\",\"secondman\":\"第二联系人papi酱\",\"alipaynum\":\" 123宝号\",\"certificatePhoto\":\"{\\\"0\\\":\\\"4335dae2-0143-481a-ae45-679e2bee2f68\\\",\\\"1\\\":\\\"8ab99614-e47f-4937-bf0a-1b8b10f29d59\\\"}\",\"corporationId\":\"1101A\",\"occupation\":\"无\",\"taccountbank\":\"gongshang \",\"classname\":\"Net\",\"certificateexpiredate\":\"2026-03-01\",\"couserStairClassify\":\"语文\",\"workCompanyNature\":\"\",\"qqnum\":\"179854857\",\"hmac\":\"434155c9c8279e7461c98711c12cd5f8\",\"address\":\"申请人地址\",\"urlkey\":\"8ab99614-e47f-4937-bf0a-1b8b10f29d59|4335dae2-0143-481a-ae45-679e2bee2f68|4335dae2-0143-481a-ae45-679e2bee2f68\",\"firstmobile\":\"13881060959\",\"firstman\":\"第一联系人pipipapa酱\",\"handIdCardPhoto\":\"4335dae2-0143-481a-ae45-679e2bee2f68\",\"tcaccount\":\"6225880111111112\"}";
    try {
      PersonFactor personFactor=jsonMapper.readValue(json, PersonFactor.class);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
