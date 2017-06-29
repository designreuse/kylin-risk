package com.rkylin.risk.service.biz.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import com.rkylin.facerecognition.api.service.LinkFaceOCRService;
import com.rkylin.facerecognition.api.vo.BankCardEntity;
import com.rkylin.facerecognition.api.vo.BaseResultVo;
import com.rkylin.facerecognition.api.vo.ImageEntity;
import com.rkylin.risk.service.ApiServiceConstants;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.IdentifyBiz;
import com.rkylin.risk.service.utils.MulThreadRSAUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
public class IdentifyBizImplTest {

  @Resource
  private IdentifyBiz identifyBiz;

  @Resource
  private LinkFaceOCRService linkFaceOCRService;

  @Test
  public void identifyTest() throws Exception {
    PersonFactor personFactor = new PersonFactor();
    personFactor.setUserid("userid666666");
    personFactor.setUserrelatedid("100ew78");
    personFactor.setConstid("M000004");
    personFactor.setUsername("张亚楠");
    personFactor.setCertificatenumber("610124199011212422");
    personFactor.setCertificatestartdate("2016-07-13");
    personFactor.setCertificateexpiredate("2026-07-13");
    personFactor.setMobilephone("123456");
    personFactor.setAge("0");
    personFactor.setEducation("4");
    personFactor.setCheckorderid("15616");
    personFactor.setClassname("IOS");
    personFactor.setClassprice("5000");
    personFactor.setTcaccount("232323232");

    personFactor.setCourseId("k01");
    personFactor.setCorporationId("xuexiao01");
    personFactor.setCorporationname("融数大学");
    personFactor.setCouserStairClassify("课程一级分类");
    personFactor.setCourseSecondaryClassify("课程二级分类");

    String hmacString = new StringBuilder(personFactor.getUserid())
        .append(personFactor.getConstid())
        .append(personFactor.getUsername())
        .append(personFactor.getClassname())
        .append(ApiServiceConstants.ORDER_API_HMAC)
        .toString();
    personFactor.setHmac(DigestUtils.md5Hex(hmacString));
    personFactor.setUrlkey("d7e4902a-ec60-43e5-b365-c8b40bba29d2");
    personFactor.setFirstman("aa");
    personFactor.setFirstmobile("135");
    personFactor.setSecondman("bb");
    personFactor.setSecondmobile("136");
    personFactor.setMobilelist("156564843");
    /*Map<String, Object> imageEntityMap = identifyBiz.unzip(Files.readAllBytes(
        Paths.get("d:/hh.zip")));
    identifyBiz.requestFaceresult(imageEntityMap, personFactor);
*/
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

  @Test
  public void bankTest() throws IOException {
    RpcContext.getContext().setAttachment("appId", "fengkong");
    RpcContext.getContext().setAttachment("appKey", "a1b2c3d4f5");
    RpcContext.getContext().setAttachment("appSecret", "1a2b3c4d5f");
    byte[] b=Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\Desktop\\bankcard.png"));
    String md5CheckSum = DigestUtils.md5Hex(b);
    ImageEntity entity = new ImageEntity();
    entity.setType(ImageEntity.Type.BYTES);
    entity.setData(b);
    BaseResultVo<BankCardEntity> bankresult=linkFaceOCRService.getBankCardInfo("151", entity);
    System.out.println(md5CheckSum);
    System.out.println(bankresult.toString());
  }



  public static void main(String[] args) throws Exception {
    String key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCco2i+bVZMi4ihzHMeTzgNC6vYiGxUzbSTNBglt4HeJVyJZ6i6RTHCnMf5pC9G5Gw/BmhAU/xjUbf0H8BPNUvc1mOlDbSdK5gE2uyLy6oD0p6d5co4YJnni+QwkUaU15QCQIl4gVLqbzxWdiV+F6ntYRnxP4sJP2YxQ3T8/xWiVQIDAQAB";
    byte[] dencodedDate = null;
    byte[] b=Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\Desktop\\3.png"));
    dencodedDate = MulThreadRSAUtils.decryptByPublicKey(b, key);
     Files.write(Paths.get("C:\\Users\\Administrator\\Desktop\\c.png"),dencodedDate);
  }
}
