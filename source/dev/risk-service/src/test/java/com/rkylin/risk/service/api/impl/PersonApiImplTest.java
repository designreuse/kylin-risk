package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.operation.api.PersonApi;
import com.rkylin.risk.operation.bean.PersonMsg;
import com.rkylin.risk.service.bean.PersonFactor;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cuixiaofang on 2016-7-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class PersonApiImplTest {
  @Resource
  private PersonApi personApi;
  @Resource
  private ObjectMapper jsonMapper;
  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void isPassPersonmsgTest() throws JsonProcessingException {
/*    PersonFactor personFactor = new PersonFactor();
    //911606774409171136
    personFactor.setUserid("9898");
    personFactor.setUserrelatedid("991607963239232110");
    personFactor.setConstid("M000004");
    personFactor.setUsername("邱显");
    personFactor.setCertificatenumber("362227199111231238");

    personFactor.setMobilephone("18770056772");
    personFactor.setAge("26");
    personFactor.setEducation("4");
    personFactor.setCheckorderid("25742");
    personFactor.setClassname("学历自考专升本课程");
    personFactor.setClassprice("5000");
    personFactor.setTcaccount("6214830121258155");

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
    //701e7298-3aed-43ca-b908-ef502496852b
    //ab3c31dc-8b0b-4b35-bfa8-e6e386ad7d43
    //b4cfb653-990a-4d05-be21-0af728611162|df65e579-40d1-4502-a228-b51b1c830bc7|81fef6a8-c50f-4b0f-9d64-c8025788082c
    //"22d30933-9102-489a-a50d-5f1a5b178d18|3763ed5d-6efc-418f-8e5f-a3aa71169749|338fcb06-ee3e-4695-9bf0-a10d05d35e2e"
    //3763ed5d-6efc-418f-8e5f-a3aa71169749|338fcb06-ee3e-4695-9bf0-a10d05d35e2e

    personFactor.setUrlkey(
        "edfaf93f-77ca-47ea-a259-2ed321382ebc");*/
    //String jsonStr = objectMapper.writeValueAsString(personFactor);
    //String jsonStr="{\"firstrelation\":\"父母\",\"secondmobile\":\"15122526400\",\"certificatestartdate\":\"1900-01-01\",\"isstudent\":\"1\",\"userrelatedid\":\"MRFQ-MT1912201610017\",\"userid\":\"o83-Vt1F_Ih8ssLjTijF-Wx6c8iM\",\"education\":\"大专\",\"taccountprovince\":\"天津市\",\"checkorderid\":\"200088\",\"mobilephone\":\"15122519067\",\"constid\":\"M000028\",\"username\":\"张思晗\",\"isauthor\":\"\",\"age\":\"21\",\"certificatenumber\":\"230321199506306202\",\"secondrelation\":\"朋友\",\"secondman\":\"马锐\",\"enrolDate\":\"2015\",\"occupation\":\"无\",\"taccountbank\":\"中国农业银行\",\"classname\":\"自体脂肪填充：额头、太阳穴 、下巴 、隆鼻 、泪沟 、卧蚕、法令纹 、丰唇 、苹果肌 、面颊\",\"certificateexpiredate\":\"2099-12-31\",\"businessType\":\"1\",\"taccountcity\":\"天津市\",\"nation\":\"汉族\",\"schoolArea\":\"本部\",\"qqnum\":\"100658510\",\"hmac\":\"9f2bb6a224e9ac7622e6222a7c072f47\",\"address\":\"230321199506306202\",\"email\":\"100658510@qq.com\",\"urlkey\":\"5098b3ff-5df4-4ef8-a0c3-7c1f87c266e3|362d9229-1809-40b4-8d20-a1cb81de781d|17780798-b7b3-4cf4-8ba6-2c59c153bdcf\",\"universityName\":\"天津天狮学院\",\"firstmobile\":\"13796921590\",\"firstman\":\"韩涛\",\"tcaccount\":\"6228480028537533870\"}";
    //String jsonStr="{\"currentFullAddress\":\"地球村\",\"certificatestartdate\":\"20010309\",\"userrelatedid\":\"10000\",\"userid\":\"UCP148394506275800001\",\"education\":\"普通本科\",\"city\":\"\",\"checkorderid\":\"\",\"mobilephone\":\"18632596584\",\"constid\":\"M000029\",\"mateaddress\":\"河南省周口市川汇区中州北路东一巷138号\",\"username\":\"张三\",\"isauthor\":\"1\",\"firstid\":\"412701199208082038\",\"age\":\"29\",\"province\":\"\",\"mobilelist\":\"[{\"email\":\"\",\"firstname\":\"Fff\",\"lastname\":null,\"mobile1\":\"\\\"12508963254\\\"\",\"mobile2\":\"\\\"136-5821-4753\\\"\",\"mobile3\":\"\\\"136-2547-8523\\\"\"},{\"email\":\"\\\"gyyyh\\\"\",\"firstname\":\"Fgvgg\",\"lastname\":null,\"mobile1\":\"\\\"136-5087-4128\\\"\",\"mobile2\":\"\",\"mobile3\":\"\"},{\"email\":\"\\\"123@sina.com\\\"\",\"firstname\":\"Fff\",\"lastname\":null,\"mobile1\":\"\\\"12508963254\\\"\",\"mobile2\":\"\\\"136-5821-4753\\\"\",\"mobile3\":\"\\\"13825478523\\\"\"}]\",\"certificatenumber\":\"135628198801012568\",\"matenation\":\"汉\",\"salary\":\"\",\"occupation\":\"地球\",\"childName\":\"\",\"classname\":\"美容测试商品\",\"checkMethod\":\"\",\"certificateexpiredate\":\"20200308\",\"matestartDate\":\"20080811\",\"matesex\":\"\",\"currentAddress\":\"地球村\",\"hmac\":\"76a12414901d3c18f6c0830128211153\",\"urlkey\":null,\"firstmobile\":\"4008308300\",\"mateurlkey\":\"20f584e9-0d97-4d45-8122-529799428935|20f584e9-0d97-4d45-8122-529799428935\",\"mateendDate\":\"20180811\",\"firstman\":\"邓斌\",\"childCardId\":\"\",\"photographyType\":\"2\",\"tcaccount\":\"12314654654876143\",\"unitFullAddress\":\"\"}";
    String jsonStr="{\n"

        + "    \"certificatenumber\": \"362227199111231238\",\n"
        + "    \"username\": \"邱显\",\n"
        + "    \"nation\": \"汉族\",\n"
        + "    \"address\": \"北京市北京市北京市北京市\",\n"
        + "    \"certificatestartdate\": \"2007-03-09\",\n"
        + "    \"certificateexpiredate\": \"2017-03-08\",\n"
        + "    \"urlkey\": \"3763ed5d-6efc-418f-8e5f-a3aa71169749|338fcb06-ee3e-4695-9bf0-a10d05d35e2e\",\n"
        + "    \"logourlkey\": \"3763ed5d-6efc-418f-8e5f-a3aa71169749\",\n"
        + "    \"contracturlkey\": \"338fcb06-ee3e-4695-9bf0-a10d05d35e2e\",\n"
        + "    \"qqnum\":\"474569443\",\n"
        + "    \"email\": \"474569443@qq.com\",\n"
        + "    \"firstman\": \"邓一二\",\n"
        + "    \"firstrelation\": \"同学\",\n"
        + "    \"firstmobile\": \"18518513265\",\n"
        + "    \"firstid\": \"362227199105101226\",\n"
        + "    \"secondman\": \"李四\",\n"
        + "    \"secondid\": \"362514196532654213\",\n"
        + "    \"secondmobile\": \"13625256363\",\n"
        + "    \"secondrelation\": \"同事\",\n"

        + "    \"mateurlkey\": \"20f584e9-0d97-4d45-8122-529799428935|20f584e9-0d97-4d45-8122-529799428935\",\n"
        + "    \"mateendDate\": \"2018-08-11\",\n"
        + "    \"matenation\": \"汉族\",\n"
        + "    \"matestartDate\": \"2008-08-11\",\n"
        + "    \"matesex\": \"女\",\n"
        + "    \"mateaddress\": \"河南省周口市川汇区中州北路东一巷138号\",\n"
        + "    \"photographyType\": \"2\",\n"

        + "    \"mobilelist\": \"[{\\\"email\\\":\\\"\\\",\\\"firstname\\\":\\\"Fff\\\",\\\"lastname\\\":null,\\\"mobile1\\\":\\\"\\\\\\\"12508963254\\\\\\\"\\\",\\\"mobile2\\\":\\\"\\\\\\\"136-5821-4753\\\\\\\"\\\",\\\"mobile3\\\":\\\"\\\\\\\"136-2547-8523\\\\\\\"\\\"},{\\\"email\\\":\\\"\\\\\\\"gyyyh\\\\\\\"\\\",\\\"firstname\\\":\\\"Fgvgg\\\",\\\"lastname\\\":null,\\\"mobile1\\\":\\\"\\\\\\\"136-5087-4128\\\\\\\"\\\",\\\"mobile2\\\":\\\"\\\",\\\"mobile3\\\":\\\"\\\"},{\\\"email\\\":\\\"\\\\\\\"123@sina.com\\\\\\\"\\\",\\\"firstname\\\":\\\"Fff\\\",\\\"lastname\\\":null,\\\"mobile1\\\":\\\"\\\\\\\"12508963254\\\\\\\"\\\",\\\"mobile2\\\":\\\"\\\\\\\"136-5821-4753\\\\\\\"\\\",\\\"mobile3\\\":\\\"\\\\\\\"13825478523\\\\\\\"\\\"}]\",\n"

        + "    \"currentAddress\": \"北京市海淀区\",\n"
        + "    \"currentFullAddress\": \"北京市海淀区安河桥北\",\n"
        + "    \"workCompanyAddress\": \"北京市海淀区酒仙桥\",\n"
        + "    \"unitFullAddress\": \"北京市海淀区安河桥北\",\n"
        + "    \"workCompanyName\": \"北京大学\",\n"
        + "    \"workTitle\": \"北京大学\",\n"
        + "    \"workCompanyNature\": \"本部\",\n"
        + "    \"workCompanyAddress\": \"2010\",\n"
        + "    \"salary\": \"6000\",\n"


        + "    \"tcaccount\": \"6214830121258155\",\n"
        + "    \"bankname\": \"招商银行\",\n"
        + "    \"bankcode\": \"206\",\n"
        + "    \"bankbranch\": \"回龙观支行\",\n"
        + "    \"taccountbank\": \"招商银行\",\n"
        + "    \"taccountprovince\": \"北京市\",\n"
        + "    \"taccountcity\": \"北京市\",\n"
        + "    \"taccountbranch\": \"回龙观支行\",\n"
        + "    \"isstudent\": \"0\",\n"
        + "    \"constid\": \"M000029\",\n"
        + "    \"checkorderid\": \"561436\",\n"
        + "    \"mobilephone\": \"18770056772\",\n"
        + "    \"userrelatedid\": \"10000\",\n"
        + "    \"userid\": \"UCP148394506275800001\",\n"
        + "    \"isauthor\": \"1\",\n"
        + "    \"age\": \"27\",\n"
        + "    \"classname\": \"美容测试商品\",\n"
        + "    \"checkMethod\": \"1\",\n"
        + "    \"hmac\": \"4969b9828200333033c491d32767f890\"\n"


/*
        + "    \"childCardId\": \"\",\n"
        + "    \"education\": \"1\",\n"
        + "    \"city\": \"\",\n"
        + "    \"province\": \"\",\n"
        + "    \"childName\": \"\",\n"*/










        + "}";
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



  @Test
  public void json() throws JsonProcessingException {
    PersonFactor person = null;
    String jsonStr="{\"firstrelation\":\"父母\",\"secondmobile\":\"15122526400\",\"certificatestartdate\":\"1900-01-01\",\"isstudent\":\"1\",\"userrelatedid\":\"MRFQ-MT1912201610017\",\"userid\":\"o83-Vt1F_Ih8ssLjTijF-Wx6c8iM\",\"education\":\"大专\",\"taccountprovince\":\"天津市\",\"checkorderid\":\"200088\",\"mobilephone\":\"15122519067\",\"constid\":\"M000028\",\"username\":\"张思晗\",\"isauthor\":\"\",\"age\":\"21\",\"certificatenumber\":\"230321199506306202\",\"secondrelation\":\"朋友\",\"secondman\":\"马锐\",\"enrolDate\":\"2015\",\"occupation\":\"无\",\"taccountbank\":\"中国农业银行\",\"classname\":\"自体脂肪填充：额头、太阳穴 、下巴 、隆鼻 、泪沟 、卧蚕、法令纹 、丰唇 、苹果肌 、面颊\",\"certificateexpiredate\":\"2099-12-31\",\"businessType\":\"1\",\"taccountcity\":\"天津市\",\"nation\":\"汉族\",\"schoolArea\":\"本部\",\"qqnum\":\"100658510\",\"hmac\":\"9f2bb6a224e9ac7622e6222a7c072f47\",\"address\":\"230321199506306202\",\"email\":\"100658510@qq.com\",\"urlkey\":\"5098b3ff-5df4-4ef8-a0c3-7c1f87c266e3|362d9229-1809-40b4-8d20-a1cb81de781d|17780798-b7b3-4cf4-8ba6-2c59c153bdcf\",\"universityName\":\"天津天狮学院\",\"firstmobile\":\"13796921590\",\"firstman\":\"韩涛\",\"tcaccount\":\"6228480028537533870\"}";
    if (isNotEmpty(jsonStr)) {
      try {
        person = jsonMapper.readValue(jsonStr, PersonFactor.class);
      } catch (JsonParseException e) {

      } catch (JsonMappingException e) {
      } catch (IOException e) {
      }
    }
    if ("1".equals(person.getIsstudent())) {
      if (StringUtils.isEmpty(person.getSchoolArea())) {

      }
      if (StringUtils.isEmpty(person.getUniversityName())) {
      }
      if (StringUtils.isEmpty(person.getEducation())) {
      }
      if (StringUtils.isEmpty(person.getEnrolDate())) {
      }
    } else {
      if (StringUtils.isEmpty(person.getWorkCompanyName())) {
      }
    }
    System.out.println(person.toString());
  }
}
