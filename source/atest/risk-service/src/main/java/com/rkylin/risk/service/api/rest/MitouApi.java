package com.rkylin.risk.service.api.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.service.api.ApiMitouConstants;
import com.rkylin.risk.service.bean.MitouGetUserInfoResponseParam;
import com.rkylin.risk.service.bean.MitouResponseParam;
import com.rkylin.risk.service.bean.MitouSigninRequestParam;
import com.rkylin.risk.service.bean.MitouSigninResponseParam;
import com.rkylin.risk.service.bean.MitouSignupRequestParam;
import com.rkylin.risk.service.bean.MitouSignupResponseParam;
import com.rkylin.risk.service.biz.MitouBiz;
import com.rkylin.risk.service.resteasy.Encrypt;
import com.rkylin.risk.service.resteasy.component.converter.bean.MitouRequestBean;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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

  @Value("${mitou.domain}")
  private String domain;

  @Value("${mitou.platid}")
  private int platid;

  @Value("${mitou.publicKey}")
  private String publicKey;

  @Resource
  private OperateFlowService operateFlowService;

  private static Cache<String, URI> map;

  private static Cache<String, Object> requestSet;

  static {
    map = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
    requestSet = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
  }

  @Path("redirectService")
  @GET
  public Response skip(@QueryParam("userid") final String userid,
      @QueryParam("mobile") final String mobile,
      @QueryParam("backurl") final String backurl) {

    log.info("------------【米投服务转跳】流程 开始-------------");
    long startTime = System.currentTimeMillis();   //获取开始时间

    log.info("【米投服务跳转】接收参数: 用户ID:{}, 手机号:{}, 跳转地址:{}", userid, mobile, backurl);

    // 注册
    MitouResponseParam<MitouSignupResponseParam> registerResponse = register(userid, mobile);
    log.info("【米投服务跳转】获取到注册信息: {}, ID: {}", registerResponse, userid);

    if (registerResponse.getFlag() == 1) {
      log.info("【米投服务跳转】userid: {}, mobile: {} 注册失败", userid, mobile);
      return Response.ok("Your Signup Failed!").status(403).build();
    }

    // 获取token
    MitouResponseParam<MitouSigninResponseParam> getTokenResponse =
        getToken(userid, mobile);

    log.info("【米投服务跳转】获取登录token请求: {}", getTokenResponse);

    String token = getTokenResponse.getData().getTocken();

    log.info("【米投服务跳转】获取到的token是:{}", token);

    URI redirectURI = UriBuilder.fromUri(domain + LOGIN_API)
        .queryParam("back_url", backurl)
        .queryParam("token", token)
        .queryParam("only_phone", VALID_MOBILE).build();

    log.info("------------【米投服务转跳】流程 结束,请求跳转到{}-------------", redirectURI.toString());
    map.put(userid, redirectURI);
    long endTime = System.currentTimeMillis();
    log.info("程序运行时间： " + (endTime - startTime) + "ms");
    return Response.temporaryRedirect(redirectURI).build();
  }

  private MitouResponseParam<MitouSigninResponseParam> getToken(String userid, String mobile) {
    MitouSigninRequestParam requestParam = new MitouSigninRequestParam();

    log.info("【米投服务转跳】获取token的需要的用户ID:{}, 手机号:{}", userid, mobile);

    requestParam.setPlatuserid(userid);
    requestParam.setMobile(mobile);
    MitouResponseParam<MitouSigninResponseParam> response =
        mitouBiz.signin(platid, requestParam);
    return response;
  }

  private MitouResponseParam<MitouSignupResponseParam> register(String userid, String mobile) {
    MitouSignupRequestParam requestParam = new MitouSignupRequestParam();
    requestParam.setPlatuserid(userid);
    requestParam.setMobile(mobile);
    MitouResponseParam<MitouSignupResponseParam> response =
        mitouBiz.signup(platid, requestParam);
    return response;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  private static class MitouNotfiyResult {
    @JsonProperty("receive_status")
    private int status;
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
        if (ApiMitouConstants.ACCEPT.equals(responseParam.getMainStatus())
            || ApiMitouConstants.REFUSE.equals(responseParam.getMainStatus())
            || ApiMitouConstants.FOREVER_REFUSE.equals(responseParam.getMainStatus())) {
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
            operateFlow.setMtverifystatus(
                responseParam.getCardIdStatus()
                    + "|"
                    + responseParam.getContactStatus()
                    + "|"
                    + responseParam.getMobileAuthStatus()
                    + "|"
                    + responseParam.getStudentInfoStatus());
            operateFlow.setMtverifyreason(
                UnicodeToChinese(responseParam.getCardIdReason())
                    + "|"
                    + UnicodeToChinese(responseParam.getContactReason())
                    + "|"
                    + UnicodeToChinese(responseParam.getMobileAuthReason())
                    + "|"
                    + UnicodeToChinese(responseParam.getStudentReason())
            );
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

  public static String UnicodeToChinese(String unicodestring) {
    log.info("unicodestring----------------" + unicodestring);
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
}