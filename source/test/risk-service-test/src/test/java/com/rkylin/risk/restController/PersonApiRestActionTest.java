package com.rkylin.risk.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.test.dto.PersonFactor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by tomalloc on 16-9-19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:/spring-dubbo-consumer-test.xml", "classpath:/spring-mvc.xml"})
public class PersonApiRestActionTest {
  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  private ObjectMapper mapper = new ObjectMapper();

  @Before
  public void setup() throws Exception {

    this.mockMvc = webAppContextSetup(this.wac).build();
  }

  @Test
  public void orderCheckTest() throws Exception {
    PersonFactor personFactor = new PersonFactor();
    personFactor.setUserid("133456899");
    personFactor.setUserrelatedid("abc123");
    personFactor.setCheckorderid("123456789");
    personFactor.setConstid("M000004");
    personFactor.setUsername("张三");
    personFactor.setCertificatenumber("222402198408030419");
    personFactor.setCertificatestartdate("2010-05-03");
    personFactor.setCertificateexpiredate("2020-05-03");
    personFactor.setTcaccount("6222020903001483077");
    personFactor.setMobilephone("18629273432");
    personFactor.setAge("32");
    personFactor.setClassname("Java基础");
    personFactor.setClassprice("10000");

    //personFactor.setUrlkey("1232d");
    //personFactor.setFirstman("李白");
    //personFactor.setFirstmobile("13716823969");
    //personFactor.setSecondman("张作霖");
    //personFactor.setMobilephone("15839182349");
    String personFactorStr = mapper.writeValueAsString(personFactor);
    System.out.println(personFactorStr);
    this.mockMvc.perform(post("/persionApi/personmsg")
        .contentType(MediaType.APPLICATION_JSON).content(personFactorStr))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"));
  }
}
