package com.rkylin.risk.credit.service;

import com.rkylin.risk.commons.entity.ExternalCreditResult;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import com.rkylin.risk.credit.biz.CreditResultPersistent;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-7-31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-credit-py-test.xml"})
public class PYApiTest {
  @Resource
  private PYBankCheckApi pyBankCheckApi;

  @Resource
  private PYQueryHistoryApi pyQueryHistoryApi;

  @Resource
  private PYCreditReportApi pyCreditReportApi;

  private CreditRequestParam requestParam() {
    CreditRequestParam param = new CreditRequestParam();
    param.setName("田莹莹");
    param.setMobile("13470027820");
    param.setIdNumber("210922199406130348");
    param.setBankCard("6228482198209310572");
    return param;
  }

  private void request(PYApi pyApi, CreditRequestParam param) {
    pyApi.setCreditResultPersistent(new CreditResultPersistent() {

      @Override public void persistent(ExternalCreditResult creditResult) {

      }

      @Override public ExternalCreditResult queryCommonCreditResult(String idNumber,
          CreditProductType creditProductType, String module) {
        return null;
      }

      @Override public ExternalCreditResult queryUnionPayCreditResult(String bankNumber,
          CreditProductType creditProductType, String module) {
        return null;
      }

      @Override public Map<CreditProductType, ExternalCreditResult> query(String id) {
        return null;
      }
    });
    Object obj = pyApi.request(param);

    XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
    String json = xStream.toXML(obj);
    System.out.println(json);
    assertThat(json).isNotEmpty();
  }

  @Test
  public void bankcheckRequestTest() {
    request(pyBankCheckApi, requestParam());
  }

  @Test
  public void queryHistoryRequestTest() {
    request(pyQueryHistoryApi, requestParam());
  }

  @Test
  public void creditReportRequestTest() {
    CreditRequestParam param = new CreditRequestParam();
    param.setName("田莹莹");
    param.setIdNumber("411381198708213932");
    request(pyCreditReportApi, param);
  }
}
