package com.rkylin.risk.service.biz.impl;

import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.IdentifyBiz;
import com.rkylin.risk.service.biz.YueshijueBiz;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 证件识别测试
 *
 * @author qiuxian
 * @create 2016-08-29 17:26
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class YueshijueImplTest {

  @Resource
  private YueshijueBiz yueshijueBiz;

  @Resource
  private IdentifyBiz identifyBiz;

  @Test
  public void YueshijueImplTest() throws Exception {
    PersonFactor personFactor = new PersonFactor();
    //911606774409171136
    personFactor.setUserid("798");
    personFactor.setUserrelatedid("991607963239232110");
    personFactor.setConstid("M000025");
    personFactor.setUsername("邱显");
    personFactor.setCertificatenumber("362227199111231238");

    personFactor.setMobilephone("15600220022");
    personFactor.setAge("26");
    personFactor.setEducation("4");
    personFactor.setCheckorderid("456951");
    personFactor.setClassname("学历自考专升本课程");
    personFactor.setClassprice("5000");
    personFactor.setTcaccount("6228480018676705371");

    personFactor.setCourseId("1003506");
    personFactor.setCorporationId("11914");
    personFactor.setCorporationname("融数大学");
    personFactor.setCouserStairClassify("课程一级分类");
    personFactor.setCourseSecondaryClassify("课程二级分类");

    personFactor.setNation("汉族");
    personFactor.setAddress("江西省南昌市");
    personFactor.setCertificatestartdate("2012-10-22");
    personFactor.setCertificateexpiredate("2022-10-22");
    personFactor.setQqnum("474569443");
    personFactor.setFirstman("qwe");
    personFactor.setFirstmobile("12345678914");
    personFactor.setFirstrelation("朋友");
    personFactor.setSecondman("asd");
    personFactor.setSecondmobile("13625845623");
    personFactor.setSecondrelation("同学");
    personFactor.setTaccountbank("中国农业银行");
    personFactor.setTaccountbranch("酒仙桥支行");
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

    //Map<String, Object> imageEntityMap = identifyBiz.unzip(Files.readAllBytes(
    //    Paths.get("d:/yyy.zip")));
    //yueshijueBiz.requestMitouRisk(imageEntityMap, personFactor);

    try {
      TimeUnit.MINUTES.sleep(20);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //  identifyBiz.requestFaceresult(imageEntityMap, personFactor);

    // identifyBiz.downloadFile("ba05b8d1-527d-4c20-b87f-999bad9e4ef2");
    //identifyBiz.cresdit("邱显", "362227199111231244");
    //identifyBiz.test();

  /*
  public void cresdit(String name, String num) {
    CommonDto dto = new CommonDto();
    dto = setBasicInfo(dto);
    dto.setQueryName(name);
    dto.setIdType(0);
    dto.setIdCode(num);
    PersonBaseInfoRespDto response = creditService.personBasicInfo(dto);
    log.info(response.getCisReport().getPersonBaseInfo().toString());
    String status = response.getCisReport().getPersonBaseInfo().getVerifyResult();
    response.getCisReport().getPersonBaseInfo().getName();
    response.getCisReport().getPersonBaseInfo().getDocNo();
  }
 */




/*    ImageEntity imageEntity1 = (ImageEntity)imageEntityMap.get("idcard");
    // log.info("idcard:" + imageEntity1.toString());
    ImageEntity imageEntity2 = (ImageEntity)imageEntityMap.get("idcardBack");
    //log.info("idcard_back:" + imageEntity1.toString());
    ImageEntity imageEntity3 = (ImageEntity)imageEntityMap.get("idcardPersonPic");
    //log.info("idcard_person_pic:" + imageEntity1.toString());
    ImageEntity imageEntity4 = (ImageEntity)imageEntityMap.get("bankCard");
    // log.info("bankcard:" + imageEntity1.toString());

    setAttachValues();
    //身份证正反面
    BaseResultVo<IdCardEntity> idcardresult = linkFaceOCRService.getIdCardInfo(imageEntity1,
        imageEntity2);
    log.info("idcardresult:" + idcardresult.toString());

    //银行卡
    setAttachValues();
    BaseResultVo<BankCardEntity> bankresult = linkFaceOCRService.getBankCardInfo(imageEntity4);
    log.info("bankresult:" + bankresult.toString());
    //个人照片与身份证对比
    setAttachValues();
    BaseResultVo<String> personresult = faceRecognitionService.photoMatchIdCard(imageEntity2,
        imageEntity3);

    log.info("personresult:" + personresult.toString());

    log.info(getMessage(idcardresult)+getMessage(bankresult)+getMessage(personresult));*/
  }
}
