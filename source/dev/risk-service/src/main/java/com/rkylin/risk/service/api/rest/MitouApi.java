package com.rkylin.risk.service.api.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.CustomerRequestIP;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.CustomerRequestIpService;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.service.api.ApiMitouConstants;
import com.rkylin.risk.service.bean.MitouGetFirstAreaRequestParam;
import com.rkylin.risk.service.bean.MitouGetFirstAreaResponseParam;
import com.rkylin.risk.service.bean.MitouGetSchoolAreaRequestParam;
import com.rkylin.risk.service.bean.MitouGetSchoolAreaResponseParam;
import com.rkylin.risk.service.bean.MitouGetSchoolRequestParam;
import com.rkylin.risk.service.bean.MitouGetSchoolResponseParam;
import com.rkylin.risk.service.bean.MitouGetSonAreaRequestParam;
import com.rkylin.risk.service.bean.MitouGetSonAreaResponseParam;
import com.rkylin.risk.service.bean.MitouGetUserInfoResponseParam;
import com.rkylin.risk.service.bean.MitouResponseParam;
import com.rkylin.risk.service.bean.MitouSigninRequestParam;
import com.rkylin.risk.service.bean.MitouSigninResponseParam;
import com.rkylin.risk.service.bean.MitouSignupRequestParam;
import com.rkylin.risk.service.bean.MitouSignupResponseParam;
import com.rkylin.risk.service.biz.MitouBiz;
import com.rkylin.risk.service.resteasy.Encrypt;
import com.rkylin.risk.service.resteasy.component.converter.bean.MitouRequestBean;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.annotations.cache.Cache;
import org.springframework.beans.factory.annotation.Value;

import static com.rkylin.risk.service.api.ApiMitouConstants.LOGIN_API;
import static com.rkylin.risk.service.api.ApiMitouConstants.VALID_MOBILE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by tomalloc on 16-11-15.
 */
@Path("mitou")
@Slf4j
@Named
public class MitouApi {

  @Resource
  private MitouBiz mitouBiz;
  @Resource
  private ObjectMapper jsonMapper;

  @Value("${mitou.domain}")
  private String domain;

  @Value("${mitou.platid}")
  private int platid;

  @Value("${mitou.publicKey}")
  private String publicKey;

  @Value("${mitou.testMode}")
  private String testMode;

  @Resource
  private OperateFlowService operateFlowService;
  @Resource
  private CustomerRequestIpService customerRequestIpService;

  private static com.google.common.cache.Cache<String, URI> cacheURI;

  private static com.google.common.cache.Cache<String, Object> cacheUser;

  static {
    cacheURI = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
    cacheUser = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
  }

  @Path("redirectService")
  @GET
  public Response redirect(@QueryParam("userid") final String userid,
      @QueryParam("mobile") final String mobile,
      @QueryParam("backurl") final String backurl,
      @QueryParam("only_phone") final String only_phone,
      @QueryParam("h5_app") final String h5_app) {

    log.info("------------>米投服务跳转开始<-------------");
    long startTime = System.currentTimeMillis();   //获取开始时间
    log.info("接收参数: 用户ID:{}, 手机号:{}, 跳转地址:{}, 客户端类型:{}, 验证授权类型:{}", userid, mobile, backurl, h5_app, only_phone);

    // 测试模式如果是1直接跳转到backurl
    if (Constants.TRUE.equals(testMode)) {
      log.info("米投转跳服务：-------------测试模式，跳过运营商和电商授权----------");
      return Response.temporaryRedirect(UriBuilder.fromPath(backurl).build()).build();
    }

    URI uri = cacheURI.getIfPresent(userid);

    if (uri != null) {
      log.info("已经处理过该用户请求，请求跳转到: {}", uri.toString());
      return Response.temporaryRedirect(uri).build();
    }

    if (cacheUser.getIfPresent(userid) != null) {
      Object lock = cacheUser.getIfPresent(userid);
      log.info("用户在缓存中，已经获取到锁lock");
      synchronized (lock) {
        log.info("用户ID: {}请求等待", userid);
        try {
          lock.wait(TimeUnit.SECONDS.toMillis(5));
        } catch (InterruptedException e) {
          log.error("浏览器请求多次，等待之前执行结果异常", e);
        }
        uri = cacheURI.getIfPresent(userid);
        if (uri != null) {
          log.info("已经处理过该用户请求，请求跳转到{}", uri.toString());
          return Response.temporaryRedirect(uri).build();
        }
        log.warn("警告:米投执行异常，用户id {}", userid);
      }
    }

    Object lock = new Object();

    synchronized (lock) {

      cacheUser.put(userid, lock);

      String token = "";

      URI redirectURI = UriBuilder.fromPath(backurl).build();

      try {
        // 注册
        MitouResponseParam<MitouSignupResponseParam> registerResponse = register(userid, mobile, h5_app);
        log.info("【米投服务跳转】获取到注册信息: {}", registerResponse);

        if (registerResponse == null || registerResponse.getFlag() != 0) {
          log.info("-------- userid: {}, mobile: {} 的用户注册失败 --------", userid, mobile);
          // return Response.ok("<html><meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/><meta charset='utf-8'><div><h2>注册失败</h2></div></html>").status(500).build();
        }

        // 获取token
        MitouResponseParam<MitouSigninResponseParam> getTokenResponse =
            getToken(userid, mobile, h5_app);

        log.info("-------请求获取token登陆成功，获取到的信息: {}--------", getTokenResponse);

        if (getTokenResponse == null || getTokenResponse.getFlag() != 0) {
          log.info("-------- 获取Token失败 -----------");
          //return Response.temporaryRedirect(redirectURI).build();
        }

        token = getTokenResponse.getData().getTocken();
        log.info("【米投服务跳转】获取到的token是:{}", token);

      } catch (Exception e) {
        log.info("---------- 跳转服务出现异常 --------------", e);
        //return Response.temporaryRedirect(redirectURI).build();
      }

      if (StringUtils.isBlank(only_phone) && StringUtils.isBlank(h5_app)) {
        log.info("------------ 兼容旧版本没有指定电商运营商和类型-------------");
        redirectURI = UriBuilder.fromUri(domain + LOGIN_API)
            .queryParam("back_url", backurl)
            .queryParam("token", token)
            .queryParam("only_phone", VALID_MOBILE).build();
      } else {
        log.info("------------ 最新的指定了指定电商运营商:{} 和类型:{} -------------", only_phone, h5_app);
        redirectURI = UriBuilder.fromUri(domain + LOGIN_API)
            .queryParam("back_url", backurl)
            .queryParam("token", token)
            .queryParam("h5_app", h5_app)
            .queryParam("only_phone", only_phone).build();
      }

      cacheURI.put(userid, redirectURI);
      lock.notifyAll();
      log.info("提交地址---->" + redirectURI.toString());
      log.info("------------>米投服务跳转结束<-------------");
      long endTime = System.currentTimeMillis(); // 系统结束时间
      log.info("程序运行时间： " + (endTime - startTime) + "ms");

      return Response.temporaryRedirect(redirectURI).build();
    }
  }

  private MitouResponseParam<MitouSigninResponseParam> getToken(String userid, String mobile, String h5_app) throws Exception {
    MitouSigninRequestParam requestParam = new MitouSigninRequestParam();
    requestParam.setPlatuserid(userid);
    requestParam.setMobile(mobile);
    if (StringUtils.isBlank(h5_app)) {
      requestParam.setDataSource(0);
    } else {
      requestParam.setDataSource(Integer.valueOf(h5_app));
    }
    log.info("【米投服务跳转】---------开始调用获取token接口,参数userid:{}, mobile:{}--------", userid, mobile);
    MitouResponseParam<MitouSigninResponseParam> response =
        mitouBiz.signin(platid, requestParam);
    return response;
  }

  private MitouResponseParam<MitouSignupResponseParam> register(String userid, String mobile, String h5_app) throws Exception {
    MitouSignupRequestParam requestParam = new MitouSignupRequestParam();
    requestParam.setPlatuserid(userid);
    requestParam.setMobile(mobile);
    if (StringUtils.isBlank(h5_app)) {
      requestParam.setDataSource(0);
    } else {
      requestParam.setDataSource(Integer.valueOf(h5_app));
    }
    log.info("【米投服务跳转】---------开始进行注册操作,参数userid:{}, mobile:{}--------", userid, mobile);
    MitouResponseParam<MitouSignupResponseParam> response =
        mitouBiz.signup(platid, requestParam);
    return response;
  }

  @Path("notifyService")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public MitouNotfiyResult notify(@Encrypt @FormParam("data")
      MitouRequestBean<MitouGetUserInfoResponseParam> responseBean) {
    MitouGetUserInfoResponseParam responseParam = responseBean.getBean();
    try {
      if (responseParam != null && responseParam.getMainStatus() != null) {
        log.info("【米投通知】responseParam: {}", responseParam.toString());
        if (ApiMitouConstants.NORMAL.equals(responseParam.getMainStatus())
            || ApiMitouConstants.FOREVER_REFUSE.equals(responseParam.getMainStatus())
            || ApiMitouConstants.ACCEPT.equals(responseParam.getMainStatus())
            || ApiMitouConstants.REFUSE.equals(responseParam.getMainStatus())) {
          OperateFlow operateFlow =
              operateFlowService.queryByCustomerid(responseParam.getPlatuserid());
          if (operateFlow != null) {
            operateFlow.setMtApiStatus(ApiMitouConstants.MITOU_API_SUCCESS);
            operateFlow.setMtcreidtScore(responseParam.getCreidtScore());
            operateFlow.setMtcreditAmount(responseParam.getCreditAmount() == null ? "0"
                : String.valueOf(responseParam.getCreditAmount()));
            operateFlow.setMtReport(responseBean.getMessage());
            operateFlow.setMtAvailableAmount(responseParam.getAvailableAmount() == null ? "0"
                : String.valueOf(responseParam.getAvailableAmount()));
            operateFlow.setMtmainstatus(responseParam.getMainStatus());
            operateFlow.setSceneStatus(responseParam.getSceneStatus() == null ? "" : responseParam.getSceneStatus().toString());
            operateFlow.setMtverifystatus(
                responseParam.getCardIdStatus()
                    + "|"
                    + responseParam.getContactStatus()
                    + "|"
                    + responseParam.getMobileAuthStatus()
                    + "|"
                    + responseParam.getStudentInfoStatus()
                    + "|"
                    + responseParam.getBankInfoStatus()
                    + "|"
                    + responseParam.getWorkInfoStatus()
                    + "|"
                    + responseParam.getOnlineRetailerStatus()
                    + "|"
                    + responseParam.getMatesCardidStatus()
                    + "|"
                    + responseParam.getContractStatus()
                    + "|"
                    + responseParam.getOrderStatus()
            );
            operateFlow.setMtverifyreason(
                responseParam.getCardIdReason()
                    + "|"
                    + responseParam.getContactReason()
                    + "|"
                    + responseParam.getMobileAuthReason()
                    + "|"
                    +  responseParam.getStudentReason()
                    + "|"
                    + responseParam.getBankInfoReason()
                    + "|"
                    + responseParam.getWorkInfoReason()
                    + "|"
                    + responseParam.getOnlineRetailerReason()
                    + "|"
                    + responseParam.getMatesCardidReason()
                    + "|"
                    + responseParam.getContractReason()
                    + "|"
                    + responseParam.getOrderReason()
            );
            operateFlow.setRiskmsg(
                operateFlow.getRiskmsg() + ";米投信用评分：" + responseParam.getCreidtScore());
            operateFlowService.updateMitouResponse(operateFlow);
            return new MitouNotfiyResult(1);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new MitouNotfiyResult(0);
  }

  @Setter
  @Getter
  @AllArgsConstructor
  private static class MitouNotfiyResult {
    @JsonProperty("receive_status")
    private int status;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  private static class IPResult {
    @JsonProperty("result")
    private String result;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("ip")
    private String ip;
  }

  public static String UnicodeToChinese(String unicodestring) {
    log.info("unicodestring----------------"+unicodestring);
    try {
      if (isEmpty(unicodestring)) {
        return "";
      }
      String str = unicodestring.replace("\\u", ",");
      String[] s2 = str.split(",");
      String s1 = "";
      for (int i = 1; i < s2.length; i++) {
        s1 = s1 + (char) Integer.parseInt(s2[i], 16);
      }
      return s1;
    } catch (Exception e) {
      log.info("翻译unicode异常");
      e.printStackTrace();
      return "unicode exception";
    }
  }

  @Path("getFirstAreaService")
  @GET
  @Consumes(MediaType.APPLICATION_JSON+";charset=UTF-8")
  @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
  @Cache
  @GZIP
  public MitouResponseParam<MitouGetFirstAreaResponseParam[]> getFirstArea() {
    MitouGetFirstAreaRequestParam mitouGetFirstAreaRequestParam =
        new MitouGetFirstAreaRequestParam();
    MitouResponseParam<MitouGetFirstAreaResponseParam[]> response=new MitouResponseParam<>();
    try{
      response = mitouBiz.getFirstArea(platid, mitouGetFirstAreaRequestParam);
    }catch (Exception e){
      response.setFlag(0);
      response.setMsg("暂无数据");
    }


    return response;
  }

  @Path("getSonAreaService")
  @GET
  @Consumes(MediaType.APPLICATION_JSON+";charset=UTF-8")
  @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
  @Cache
  @GZIP
  public MitouResponseParam<MitouGetSonAreaResponseParam[]> getSonArea(
      @QueryParam("areaId") final Integer areaId) {
    MitouGetSonAreaRequestParam mitouGetSonAreaRequestParam =
        new MitouGetSonAreaRequestParam();
    mitouGetSonAreaRequestParam.setAreaId(areaId);
    MitouResponseParam<MitouGetSonAreaResponseParam[]> response = new MitouResponseParam<>();
    if(areaId!=null){
      response = mitouBiz.getSonArea(platid,mitouGetSonAreaRequestParam);
    }else{
      response.setFlag(1);
      response.setMsg("上级地域id不能为空");
    }

    return response;
  }

  @Path("getSchoolService")
  @GET
  @Consumes(MediaType.APPLICATION_JSON+";charset=UTF-8")
  @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
  @Cache
  @GZIP
  public MitouResponseParam<MitouGetSchoolResponseParam[]> getSchool(
      @QueryParam("areaId") final Integer areaId, @QueryParam("areaId1") final Integer areaId1,
      @QueryParam("schoolName") final String schoolName) {
    MitouGetSchoolRequestParam mitouGetSchoolRequestParam =
        new MitouGetSchoolRequestParam();
    mitouGetSchoolRequestParam.setAreaId(areaId);
    mitouGetSchoolRequestParam.setAreaId1(areaId1);
    mitouGetSchoolRequestParam.setSchoolName(schoolName);
    MitouResponseParam<MitouGetSchoolResponseParam[]> response = new MitouResponseParam<>();
    try{
      response=mitouBiz.getSchool(platid, mitouGetSchoolRequestParam);
    }catch (Exception e){
      response.setFlag(0);
      response.setMsg("暂无数据");
    }

    return response;
  }

  @Path("getSchoolAreaService")
  @GET
  @Consumes(MediaType.APPLICATION_JSON+";charset=UTF-8")
  @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
  @Cache
  @GZIP
  public MitouResponseParam<MitouGetSchoolAreaResponseParam[]> getSchoolArea(
      @QueryParam("schoolId") final Integer schoolId) {
    MitouGetSchoolAreaRequestParam mitouGetSchoolAreaRequestParam =
        new MitouGetSchoolAreaRequestParam();
    MitouResponseParam<MitouGetSchoolAreaResponseParam[]> response =new MitouResponseParam<>();
    if(schoolId!=null){
      mitouGetSchoolAreaRequestParam.setSchoolId(schoolId);
      try{
        response=mitouBiz.getSchoolArea(platid,  mitouGetSchoolAreaRequestParam);
      }catch (Exception e){
        response.setFlag(0);
        response.setMsg("暂无数据");
      }
    }else{
      response.setFlag(1);
      response.setMsg("学校id不能为空");
    }
    return response;
  }

  @Path("getIpService")
  @GET
  @Consumes(MediaType.APPLICATION_JSON+";charset=UTF-8")
  @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
  @Cache
  @GZIP
  public IPResult  getIP(@Context HttpServletRequest request) {
    try{
      String ip = request.getHeader("X-Forwarded-For");
      String userid=request.getParameter("userid");
      String constid=request.getParameter("constid");
      if(isEmpty(userid)||isEmpty(constid)){
        return new IPResult("false","用户号跟渠道号不能为空","");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getRemoteAddr();
          if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){
            //根据网卡取本机配置的IP
            InetAddress inet=null;
            try {
              inet = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
              e.printStackTrace();
            }
            ip= inet.getHostAddress();
          }
        }
      } else if (ip.length() > 15) {
        String[] ips = ip.split(",");
        for (int index = 0; index < ips.length; index++) {
          String strIp = (String) ips[index];
          if (!("unknown".equalsIgnoreCase(strIp))) {
            ip = strIp;
            break;
          }
        }
      }
      CustomerRequestIP customerRequestIP =new CustomerRequestIP();
      customerRequestIP.setCustomerid(userid);
      customerRequestIP.setConstid(constid);
      customerRequestIP.setRequestip(ip);
      customerRequestIpService.addCustomerRequestIP(customerRequestIP);
      return new IPResult("true","",ip);
    }catch (Exception e){
      e.printStackTrace();
      return new IPResult("false","异常","");

    }
  }
}