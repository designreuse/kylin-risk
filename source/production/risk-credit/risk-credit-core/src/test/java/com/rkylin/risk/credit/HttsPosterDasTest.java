package com.rkylin.risk.credit;

import com.bfd.facade.MerchantServer;
import com.br.bean.DasBean;
import java.util.Date;
import java.util.HashMap;
import net.sf.json.JSONObject;

public class HttsPosterDasTest {

  public static void main(String[] args) throws Exception {
    System.out.println(HashMap.Entry.class.getName());
    System.out.println(HashMap.Entry.class.getCanonicalName());
    MerchantServer ms = new MerchantServer();
    //登陆
    String loginResult = ms.login("rongshu", "rongshu");
    JSONObject json = JSONObject.fromObject(loginResult);
    System.out.println(loginResult);

    String tokenid = json.getString("tokenid");
    DasBean dasBean = new DasBean();
    dasBean.setApiType("das");

    dasBean.setTokenid(tokenid);
    dasBean.setId("130529198710080013");
    dasBean.setCell("15011521319");
    dasBean.setName("张民");
    dasBean.setTokenid(tokenid);
    dasBean.setMeal("EduLevel");
    long begin = new Date().getTime();
    String portraitResult = ms.getApiData(dasBean);
    long end = new Date().getTime();

    System.out.println("time:" + (end - begin) + " ms");
    System.out.println("result:" + portraitResult);
    System.out.println("------------------------------------------");
  }
}
