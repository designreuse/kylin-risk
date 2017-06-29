package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.rkylin.risk.operation.api.PersonApi;
import com.rkylin.risk.operation.bean.PersonMsg;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.bean.PersonFactor;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
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
        personFactor.setUserid("userid06");
        personFactor.setUserrelatedid("10040");
        personFactor.setConstid("P000008");
        personFactor.setUsername("");
        personFactor.setCertificatenumber("");
        personFactor.setCertificateexpiredate("2010-6-7");
        personFactor.setCertificatestartdate("2016-6-7");
        personFactor.setMobilephone("123456");
        personFactor.setAge("18");
        personFactor.setEducation("高中以上");
        personFactor.setCheckorderid("115");
        personFactor.setClassname("IOS平台");
        personFactor.setClassprice("5000");
        personFactor.setTcaccount("232323232");
        String hmacString = new StringBuilder(personFactor.getUserid())
            .append(personFactor.getConstid())
            .append(personFactor.getUsername())
            .append(personFactor.getClassname())
            .append(ApiServiceConstants.ORDER_API_HMAC)
            .toString();
        personFactor.setHmac(DigestUtils.md5Hex(hmacString));
        String jsonStr = objectMapper.writeValueAsString(personFactor);
        System.out.println("参数----" + jsonStr);
        PersonMsg personMsg = personApi.personmsg(jsonStr);
        assertThat(personMsg).isNotNull();
        assertThat(personMsg.getResultCode()).isEqualTo("0");

    }

    public static void main(String[] args) {
        String[] operatorEmails = null;
        List<String> emailList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(emailList)) {
            operatorEmails = emailList.toArray(new String[emailList.size()]);
        }
        System.out.println(operatorEmails.toString());
    }


}
