package com.rkylin.risk.service.utils;

import com.Rop.api.ApiException;
import com.Rop.api.DefaultRopClient;
import com.Rop.api.RopRequest;
import com.Rop.api.RopResponse;
import com.Rop.api.request.ExternalSessionGetRequest;
import com.Rop.api.response.ExternalSessionGetResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lina on 2016-8-8.
 */
@Slf4j
public class ROPUtil {
  @Setter
  private String appKey;

  @Setter
  private String appSecret;

  @Setter
  private String ropUrl;

  @Setter
  private ObjectMapper mapper;

  public <T extends RopResponse> T getResponse(RopRequest<T> request, String jsonOrXml) {
    System.setProperty("https.protocols", "TLSv1");
    DefaultRopClient ropClient =
        new DefaultRopClient(ropUrl, appKey, appSecret, jsonOrXml);
    try {
      log.info("ROP请求参数为:------------------------------------------------------");
      log.info(mapper.writeValueAsString(request));
      log.info("-------------------------------------------------------------------");

      T rsp = ropClient.execute(request, sessionGet(ropUrl, appKey, appSecret));
      //log.info("ROP返回参数为:------------------------------------------------------");
      //log.info(mapper.writeValueAsString(rsp));
      log.info("ROP请求完成----------------------------------------------------------");
      return rsp;
    } catch (ApiException e1) {
      log.error("ROP异常:{}", e1);
    } catch (JsonProcessingException e) {
      log.error("ROP异常:{}", e);
    }finally {
      System.clearProperty("https.protocols");
    }
    return null;
  }

  /**
   * 若获取到指定异常，重发
   */
  public RopResponse multiGetRespronse(RopRequest request, String jsonOrXml) {
    DefaultRopClient ropClient = new DefaultRopClient(ropUrl, appKey,
        appSecret, jsonOrXml);
    try {
      log.info("ROP请求参数为:------------------------------------------------------");
      log.info(mapper.writeValueAsString(request));
      log.info("-------------------------------------------------------------------");

      //重发两次
      RopResponse rsp = ropClient.execute(request, sessionGet(ropUrl, appKey, appSecret));
      if (rsp != null) {
        if (!rsp.isSuccess()) {
          if (rsp.getErrorCode().startsWith("S")) {
            rsp = ropClient.execute(request, sessionGet(ropUrl, appKey, appSecret));
          }
        }
      }
      log.info("ROP返回参数为:------------------------------------------------------");
      log.info(mapper.writeValueAsString(rsp));
      log.info("-------------------------------------------------------------------");
      return rsp;
    } catch (ApiException e1) {
      log.error("ROP异常:{}", e1);
    } catch (JsonProcessingException e) {
      log.error("ROP异常:{}", e);
    }
    return null;
  }

  private static String sessionGet(String ropUrl, String appKey, String appSecret) {
    String sessionKey = null;
    DefaultRopClient ropClient = new DefaultRopClient(ropUrl, appKey, appSecret);
    try {
      ExternalSessionGetRequest sessionGetReq = new ExternalSessionGetRequest();
      ExternalSessionGetResponse sessionGetRsp = ropClient.execute(sessionGetReq);
      sessionKey = sessionGetRsp.getSession();
    } catch (Exception ex) {
      log.error("ROP异常:{}", ex);
    } finally {

    }
    return sessionKey;
  }
}
