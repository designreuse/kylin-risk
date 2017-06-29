package com.rkylin.risk.service.biz;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.rkylin.risk.service.bean.MitouGetUserInfoRequestParam;
import com.rkylin.risk.service.bean.MitouGetUserInfoResponseParam;
import com.rkylin.risk.service.bean.MitouLoginRequestParam;
import com.rkylin.risk.service.bean.MitouResponseParam;
import com.rkylin.risk.service.bean.MitouRiskDataBean;
import com.rkylin.risk.service.bean.MitouRiskDataRequestParam;
import com.rkylin.risk.service.bean.MitouRiskDataResponseParam;
import com.rkylin.risk.service.bean.MitouSigninRequestParam;
import com.rkylin.risk.service.bean.MitouSigninResponseParam;
import com.rkylin.risk.service.bean.MitouSignupRequestParam;
import com.rkylin.risk.service.bean.MitouSignupResponseParam;
import com.rkylin.risk.service.resteasy.component.EncryptFormDataConverterProvider;
import com.rkylin.risk.service.resteasy.component.Jackson2Provider;
import com.rkylin.risk.service.resteasy.component.URLRequestDecodeFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-10-31.
 */
public class MitouBizTest {

  private static MitouBiz mitouBiz;
  @Resource
  private ObjectMapper jsonMapper;

  @BeforeClass
  public static void beforeClass() {
    String signupURL = "http://testapi.mitou.cn/";
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdrB4ByOSKbI3aCT10O94V5hwG"
        + "\n" + "BrXiPRRqHDDSq1bfh/VXrCo+ofKgSQRxdrZwUtQjFecOOcQUQUQL+OxHUHFJGs+i"
        + "\n" + "UYHEbFnbSolhwKXK6P6dXu34/5Ob/NGJaY9IWhSgkJBYPJrvGplk6uxVoD1krtqo"
        + "\n" + "2A8Nny2cGLkYKmFwqQIDAQAB";

    EncryptFormDataConverterProvider encryptFormDataConverterProvider =
        new EncryptFormDataConverterProvider(publicKey);
    ResteasyClient client = new ResteasyClientBuilder().build();

    // 加载组件
    client.register(Jackson2Provider.class);
    client.register(encryptFormDataConverterProvider);
    client.register(URLRequestDecodeFilter.class);

    ResteasyWebTarget target = client.target(signupURL);
    // 生成代理
    mitouBiz = target.proxy(MitouBiz.class);
  }

  @Test
  public void signupTest() {
    // 参数
    MitouSignupRequestParam signupParam = new MitouSignupRequestParam();
    signupParam.setPlatuserid("798");
    signupParam.setMobile("18770056772");
    Stopwatch stopwatch = Stopwatch.createStarted();
    // 执行
    MitouResponseParam<MitouSignupResponseParam> response = mitouBiz.signup(2, signupParam);
    System.out.println(response);
    assertThat(response).isNotNull();
    assertThat(response.getFlag()).isEqualTo(0);
    System.out.println(stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
  }

  @Test
  public void siginTest() {
    // 参数
    MitouSigninRequestParam param = new MitouSigninRequestParam();
    param.setPlatuserid("o83-VtzNAAn9wqg2MDTMmRrt55WQ");
    param.setMobile("18610010001");
    MitouResponseParam<MitouSigninResponseParam> response = mitouBiz.signin(2, param);
    System.out.println(response);
    //tocken: 8aab5d7d1b2e228efa58ce64a9f7fb13
  }

  @Test
  public void loginTest() {
    // 参数
    MitouLoginRequestParam param = new MitouLoginRequestParam();
    param.setBack_url("http://www.baidu.com/");
    param.setOnly_phone("13716035869");
    param.setToken("1f35c503b9fc56c3b13ae48aa8926b74");
    //MitouResponseParam <MitouLoginResponseParam> aa= mitouService.login(param.getToken(),param.getBack_url(),param.getOnly_phone());
    //System.out.println(response);
  }

  @Test
  public void riskDtaTest() throws IOException {
    // 参数
    MitouRiskDataRequestParam param = new MitouRiskDataRequestParam();
    MitouRiskDataBean bean = new MitouRiskDataBean();
    MitouRiskDataBean.CardId cardinfo = bean.new CardId();
    cardinfo.setCardId("610102199103202717");
    cardinfo.setName("柴文涛");
    cardinfo.setNation("汉族");
    cardinfo.setAddress("北京");
    cardinfo.setStartDate("2016-11-04");
    cardinfo.setEndDate("2016-11-04");
    cardinfo.setFrontImage("data:image/png;base64," + DatatypeConverter.printBase64Binary(
        Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\Desktop\\idcard.png"))));
    cardinfo.setReverseImage("data:image/png;base64," + DatatypeConverter.printBase64Binary(
        Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\Desktop\\idcard_back.png"))));
    cardinfo.setSelfDeclaration("data:image/png;base64," + DatatypeConverter.printBase64Binary(
        Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\Desktop\\idcard_person_pic.png"))));

    MitouRiskDataBean.Contact contactinfo = bean.new Contact();
    contactinfo.setQq("474569443");
    contactinfo.setFirstContactName("a");
    contactinfo.setFirstContactMobile("123456");
    contactinfo.setFirstContactRelationship("同学");
    contactinfo.setSecondContactName("b");
    contactinfo.setSecondContactMobile("456789");
    contactinfo.setSecondContactRelationship("老师");

    MitouRiskDataBean.EducationInfo educationInfo = bean.new EducationInfo();
    educationInfo.setEducation("大学");
    educationInfo.setEnrolDate("20160909");
    educationInfo.setSchoolArea("beij");
    educationInfo.setUniversityName("beijingdaxue");
    bean.setEducationInfo(educationInfo);

    MitouRiskDataBean.BankInfo bankinfo = bean.new BankInfo();
    bankinfo.setCardNo("53454154254546");
    bankinfo.setBankDeposit("北京银行");
    bankinfo.setBankBranch("jiuxcianq");
    bankinfo.setBankReservePhone("18770056772");
    bankinfo.setBankProvince("beijing");
    bankinfo.setBankCity("brijing");
    bean.setBankInfo(bankinfo);
    bean.setCardId(cardinfo);
    bean.setContact(contactinfo);

    param.setPlatuserid("798");
    param.setRisk_data(bean);
    MitouResponseParam<MitouRiskDataResponseParam> response = mitouBiz.riskrequest(2, param);
    System.out.println(response);
  }



  @Test
  public void getUserInfoTest() {
    // 参数
    MitouGetUserInfoRequestParam param = new MitouGetUserInfoRequestParam();
    param.setPlatuserid("798");
    MitouResponseParam<MitouGetUserInfoResponseParam> response = mitouBiz.requestUserinfo(2, param);
    System.out.println(response);
    System.out.println(response.getData().toString());
  }

  public static void main(String[] args) throws IOException {
    byte[] b= Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\Desktop\\idcard_back.png"));
    System.out.println("data:image/png;base64,"+DatatypeConverter.printBase64Binary(b));
  }

  @Test
  public void notifyfrw() {
    String publicKeys = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdrB4ByOSKbI3aCT10O94V5hwG"
        + "\n" + "BrXiPRRqHDDSq1bfh/VXrCo+ofKgSQRxdrZwUtQjFecOOcQUQUQL+OxHUHFJGs+i"
        + "\n" + "UYHEbFnbSolhwKXK6P6dXu34/5Ob/NGJaY9IWhSgkJBYPJrvGplk6uxVoD1krtqo"
        + "\n" + "2A8Nny2cGLkYKmFwqQIDAQAB";
    String data="KviC/xEJBILUM7GhXqnjUBLi6ROmQ6nDgToCmjpvuVpf+Y2R4efeQG/1t6uYs5YVtiXcz24"
        + "\n" + "XnCbaUrycL7wTEoY0p3cavZkOzdXnT78z/v9pMy5Rh/TpbmT5PWHACLb1L661WDZygdL9iUe9pBNTU"
        + "\n" + "3sHMUkF6WVgPy6Nzh5GwCI=|rCcuWXuTXGyJiUJOEKHORU/pSjWVGNY/MNrxTjnat+UZkZxVs7Zmq32"
        + "\n" + "5U1W/tiAAf11DicBMa0AuWmbYu09J0tRuZEH63MyDiY2t64tMk92URpPyjGSiAMg+z/VuKlUZRhh/FJ4A"
        + "\n" + "qS3QMQ7mWfZJv4C/wj4ykFY6prwZYJq6xDQ=|SCYlQh1E1Hcojb0tUPs+nsSs2ABFiMi9mNLFzeuDjmgcj"
        + "\n" + "uT4KBMoZFAuvp6XDng/Jfel6Q13PzSUHfMS5OqU5cTIPVMD8TEPJkFzPDrTi1TX+MNwxk63JtS6KXsijtx"
        + "\n" + "6wy5xju5DMrfujD9M0CdOioSEEqSHBS1iZlysJ3q1Jgo=|cSpsKXSw1u68x04j3t0wVmMaJknLFqzZ7TXPBK"
        + "\n" + "rjn7W8+MoqcL+isWBrGOZBNaBYFgh2nXu2eyAyMU0+PHopVzJVjNlOz1yjkponz/rLWJ3e4lL8Tcg4WWm+OTi"
        + "\n" + "zY7ttaN1fQG9XxqWc8eMyV4mBAtlE7GvCQoSeJhGraJrrnm4=";
    byte[] notifydata;
    try {
      byte[] keyBytes =  DatatypeConverter.parseBase64Binary(publicKeys);
      X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      Key publicK = keyFactory.generatePublic(x509KeySpec);
      String[] ds=data.split("\\|");
      Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
      cipher.init(Cipher.DECRYPT_MODE, publicK);
      StringBuilder sw=new StringBuilder();
      for (String d:ds){
        String notifyjson=new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(d)));
        sw.append(notifyjson);
      }
      System.out.println(new String(DatatypeConverter.parseBase64Binary(sw.toString())));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 米投通知
   */
  private MitouGetUserInfoResponseParam jsonToNotifyPara(String jsonStr) {
    MitouGetUserInfoResponseParam notifyResponse = null;
    if (isNotEmpty(jsonStr)) {
      try {
        notifyResponse = jsonMapper.readValue(jsonStr, MitouGetUserInfoResponseParam.class);
      } catch (JsonParseException e) {
        e.printStackTrace();
      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println(notifyResponse.toString());

    return notifyResponse;
  }




}
