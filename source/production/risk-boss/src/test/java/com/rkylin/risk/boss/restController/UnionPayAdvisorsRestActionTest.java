package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.dto.CreditParam;
import com.rkylin.risk.core.dto.CreditRequestEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static java.util.Collections.singletonList;

/**
 * Created by Administrator on 2016-12-19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-boss-test.xml"})
public class UnionPayAdvisorsRestActionTest {


  @Resource
  private RestTemplate restTemplate;


  @Test
  public void testRun(){
    CreditRequestEntity createReqEntity=new CreditRequestEntity();
    CreditParam creditParam=new CreditParam();
    createReqEntity.setBankCard("6228480018553696370");
    creditParam.setQueryAgain(false);
    creditParam.setQuerier("s");
    List<CreditRequestEntity> list=new ArrayList();
    list.add(createReqEntity);
    creditParam.setData(list);
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept-Charset", "utf-8");
    headers.setAccept(singletonList(MediaType.APPLICATION_JSON_UTF8));
    HttpEntity httpEntity=new HttpEntity<Object>(creditParam,headers);
    ResponseEntity<String>
        r=restTemplate.exchange("http://localhost:8088/service/api/credit/unionpay", HttpMethod.POST,httpEntity,String.class);
    //String r=restTemplate.postForObject("http://localhost:8088/service/api/credit/unionpay",creditParam,String.class);
    System.out.println(r.getBody());
  }
}
