package com.rkylin.risk.service.biz;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.rkylin.risk.service.api.ApiMitouConstants;
import com.rkylin.risk.service.bean.MitouGetFirstAreaRequestParam;
import com.rkylin.risk.service.bean.MitouGetFirstAreaResponseParam;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-10-31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class MitouBizTest {
  @Resource
  private  MitouBiz mitouBiz;
  @Resource
  private ObjectMapper jsonMapper;


  @Test
  public void signupTest() {
    // 参数
    MitouSignupRequestParam signupParam = new MitouSignupRequestParam();
    signupParam.setPlatuserid("UCP148394506275800001");
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
    param.setPlatuserid("UCP14839450627580khhk0p0001");
    param.setMobile("13810065961");
    MitouResponseParam<MitouSigninResponseParam> response = mitouBiz.signin(2, param);
    System.out.println(response);
    //tocken: f53bce3ca867c524dd837ffb08bd803b
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


    MitouRiskDataRequestParam param = new MitouRiskDataRequestParam();
    param.setDataSource(ApiMitouConstants.MITOU_DATA_SOURCE_H5);
    param.setImgDeliveryMode(ApiMitouConstants.MITOU_IMG_DELIVERY_MODE_ROP);

      param.setImgDecodeKey(ApiMitouConstants.MITOU_IMG_DECODE_KEY_YSJ);
    MitouRiskDataBean bean = new MitouRiskDataBean();
    //身份信息
    MitouRiskDataBean.CardId cardinfo = bean.new CardId();
    cardinfo.setCardId("110223199305033914");
    cardinfo.setName("陈富民");
    cardinfo.setName("陈富民");
    cardinfo.setNation("汉族");
    cardinfo.setAddress("北京市通州区greghr");
    cardinfo.setStartDate("2009-02-13");
    cardinfo.setEndDate("2019-02-13");
    String key="b4cfb653-990a-4d05-be21-0af728611162|df65e579-40d1-4502-a228-b51b1c830bc7|81fef6a8-c50f-4b0f-9d64-c8025788082c";
    String[] splitUrlkey = key.split("\\|");
    if(splitUrlkey.length==1){
      cardinfo.setFrontImage(splitUrlkey[0]);
    }else if(splitUrlkey.length==2){
      cardinfo.setFrontImage(splitUrlkey[0]);
      cardinfo.setReverseImage(splitUrlkey[1]);
    }else if (splitUrlkey.length==3){
      cardinfo.setFrontImage(splitUrlkey[0]);
      cardinfo.setReverseImage(splitUrlkey[1]);
      cardinfo.setSelfDeclaration(splitUrlkey[2]);
    }
    //联系人信息
    MitouRiskDataBean.Contact contactinfo = bean.new Contact();
    contactinfo.setQq("1005099788");
    contactinfo.setFirstContactName("寂寞");
    contactinfo.setFirstContactMobile("13810065962");
    contactinfo.setFirstContactRelationship("子女");
    contactinfo.setSecondContactName("孤单");
    contactinfo.setSecondContactMobile("13810065963");
    contactinfo.setSecondContactRelationship("同事");
    //银行信息
    MitouRiskDataBean.BankInfo bankinfo = bean.new BankInfo();
    bankinfo.setCardNo("6212260200125624797");
    bankinfo.setBankDeposit("招商银行");
    bankinfo.setBankBranch("回龙观支行支行");
    bankinfo.setBankReservePhone("13810065961");
    bankinfo.setBankProvince("北京市");
    bankinfo.setBankCity("北京市");


      MitouRiskDataBean.EducationInfo educationInfo = bean.new EducationInfo();
      educationInfo.setEducation("PUTONG_BENKE");
      educationInfo.setEnrolDate("20100908");
      educationInfo.setSchoolArea("本部");
      educationInfo.setUniversityName("北京大学");
      bean.setEducationInfo(educationInfo);

  //  bean.setCardId(cardinfo);
    bean.setContact(contactinfo);
    bean.setBankInfo(bankinfo);
    param.setPlatuserid("cccccc");
    param.setRiskData(bean);

    MitouResponseParam<MitouRiskDataResponseParam> riskrequest =
        mitouBiz.riskrequest(2, param);

    System.out.println(riskrequest);
  }



  @Test
  public void getUserInfoTest() {
    // 参数
    MitouGetUserInfoRequestParam param = new MitouGetUserInfoRequestParam();
    param.setPlatuserid("415684");
    MitouResponseParam<MitouGetUserInfoResponseParam> response = mitouBiz.requestUserinfo(2, param);
    System.out.println(response);
    System.out.println(response.getData().toString());
  }

  @Test
  public void getFirstAreaTest() {
    MitouGetFirstAreaRequestParam mitouGetFirstAreaRequestParam=new MitouGetFirstAreaRequestParam();
    mitouGetFirstAreaRequestParam.setParam("");
    MitouResponseParam<MitouGetFirstAreaResponseParam[]> response = mitouBiz.getFirstArea(2,mitouGetFirstAreaRequestParam);
    System.out.println("---------------------------------------------------");
     Map map=new HashMap<>();
    map.put("flag",response.getFlag());
    map.put("msg",response.getMsg());
    map.put("data",response.getData());

    System.out.println(response);
    JSONObject js=new JSONObject();
    String a="";
    try {
      a=jsonMapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println("-----------------------------------"+a);
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
    String data="D0ubV7z90GBxq7BwVP8YL0nvv456IAmS3hz3/y+XYTZbtLtyxcCiqW/6nBDNY/8yTdHBio49ZVqP"
        + "ZQthRX9AuCtn2TFbR05SmQzJxXLt+qURohk3wU4nrs9pF9u10qjU8Dt/yWjLlbCHUrfHa043SXsdj2mGWPNKY"
        + "ZvSx9ODkek=|sHp0Qq6mGjz/SdU86k4LGeGMyyPqTp5EkJokD2XA+FgQ5gFu+6iaFbhCfjxchTc8SxlEZfSrg3"
        + "QMYINEDJioMh8OdZcdlgIArq9UbqwFFXyh1Po2/ePPzgnpm9nXjUufCr1tmz0Q+wnwwXgLn2pbTn8sjp+nhuJTQQ3T"
        + "Kzc08MM=|J1xbhBBVAvQFGGn+utGEzEz+JncHu/VXcPlfzxgjyb6LGScQ3LkcPwr3Txu4oF7q5zs3Mx4FoR3qf54OS/b"
        + "gyVnmJYWnok4ruw4pvp08yB2JMY/v271HBdaK+25HfBunlVdQ4G6kxbxtuYBGJxXW0ILmL9OELfcAO8pGx+GUTu0=|LBt"
        + "d6d3PDbj3afcuRBVw6B9vQ/XBuxe1uBzVY+5XVQ6IY4Xmlf+4Vp5JAzJyL6nWJNeAWF8K42vYmIEwMJ6mE8D24zbtQ6Ez"
        + "C+/RWVTBkqMAsWn4fQhx0TX2kdghEJkwSnpzx4xzlNgpE74jfIqsyT215XWvwmdSiMGmS6xnNgM=|GaqoxWS0M6BHxm97"
        + "VwwgAiTTbroTd2TPDM50dbOyYzYOKCmN2PYF9GW5awmLdfDdFSAErI00g+pqETvgqRBi/NJG3W3mXp+i05wVMW8W0SVuSi"
        + "cwB9EPnJu2GTjkMIwu91j+PSyHkBDO0w8ssQPfE1cxsZt53VX2+iyGcXMq+j4=|rsdtwrq5LrWqP84xFmJCsfCSA4B1Q/X84Kk"
        + "wCPigR5FEkkkk6OKlK07d+h1IRkOfEMKSAAlKxOndjLJyAAtbXRiFZO5A9E8UeOTIJWvx1FvJ8k6g+lKHVS0g2v5NfmgXeZEy6Y4Qs"
        + "aSLli6eXAVuQtP4A+pS1IOwEThsbRDa7nM=";
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

  @Test
  public  void UnicodeToChinese() {
    String string="\\u8ba1\\u7b97\\u673a\\u7cfb\\u7edf\\u672a\\u901a\\u8fc7\\uff0c\\u8bf7\\u8865\\u5145\\u5b66\\u4fe1\\u4fe1\\u606f";
    String str = string.replace("\\u", ",");
    String[] s2 = str.split(",");
    String s1 = "";
    for (int i = 1; i < s2.length; i++) {
      s1 = s1 + (char) Integer.parseInt(s2[i], 16);
    }
    System.out.println(s1);
  }



}
