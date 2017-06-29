package com.rkylin.risk.service.api.impl;

import com.rkylin.risk.operation.api.OperateFlowStatusApi;
import com.rkylin.risk.operation.bean.ReturnInfo;
import com.rkylin.risk.service.ApiServiceConstants;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ChenFumin on 2016-10-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class OperateFlowStatusApiImplTest {

  @Autowired
  private OperateFlowStatusApi operateFlowStatusApi;

  @Test
  public void testCheckorderStatusChangeFail() {
    String checkorderid = "8100";
    String reason = "I am a test";
    String status = "1";
    String hmacString = new StringBuilder(checkorderid)
        .append(status)
        .append(ApiServiceConstants.OPER_API_HMAC)
        .toString();
    ReturnInfo returnInfo =
        operateFlowStatusApi.checkorderStatus(checkorderid, status, reason,
            DigestUtils.md5Hex(hmacString));
    Assert.assertNotNull(returnInfo);
    Assert.assertEquals("1", returnInfo.getResultCode());
    Assert.assertEquals("【风控系统】风控系统已做处理!", returnInfo.getResultMsg());
  }

  @Test
  public void testCheckorderStatusChangeSuccess() {
    String checkorderid = "8101";
    String reason = "同步成功";
    String status = "2";
    String hmacString = new StringBuilder(checkorderid)
        .append(status)
        .append(ApiServiceConstants.OPER_API_HMAC)
        .toString();
    ReturnInfo returnInfo =
        operateFlowStatusApi.checkorderStatus(checkorderid, status, reason,
            DigestUtils.md5Hex(hmacString));
    Assert.assertNotNull(returnInfo);
    Assert.assertEquals("0", returnInfo.getResultCode());
    Assert.assertEquals("【风控系统】同步成功!", returnInfo.getResultMsg());
  }

  @Test
  public void testCheckorderStatusException() {
    String checkorderid = "8101";
    String reason = "出现Exception的情况";
    String status = "3";
    String hmacString = new StringBuilder(checkorderid)
        .append(status)
        .append(ApiServiceConstants.OPER_API_HMAC)
        .toString();
    ReturnInfo returnInfo =
        operateFlowStatusApi.checkorderStatus(checkorderid, status, reason,
            DigestUtils.md5Hex(hmacString));
    Assert.assertNotNull(returnInfo);
    Assert.assertEquals("99", returnInfo.getResultCode());
    Assert.assertEquals("【风控系统】同步异常!", returnInfo.getResultMsg());
  }

}
