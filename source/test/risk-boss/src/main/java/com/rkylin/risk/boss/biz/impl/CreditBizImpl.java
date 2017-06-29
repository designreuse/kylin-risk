package com.rkylin.risk.boss.biz.impl;

import com.rkylin.risk.boss.biz.CreditBiz;
import com.rkylin.risk.core.dto.CreditParam;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Collections.singletonList;

/**
 * Created by Administrator on 2016-12-19.
 */
@Component
public class CreditBizImpl implements CreditBiz {

  @Value("${risk.service.host}")
  private String riskServiceHost;
  @Resource
  private RestTemplate restTemplate;

  @Override public String requestProxy(String url, CreditParam creditParam) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept-Charset", "utf-8");
    headers.setAccept(singletonList(MediaType.APPLICATION_JSON_UTF8));
    HttpEntity httpEntity = new HttpEntity<Object>(creditParam, headers);
    ResponseEntity<String>
        r =
        restTemplate.exchange(riskServiceHost + url, HttpMethod.POST, httpEntity,
            String.class, riskServiceHost);
    //String r=restTemplate.postForObject("http://localhost:8088/service/api/credit/unionpay",creditParam,String.class);
    return r.getBody();
  }
}
