package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.dao.OperateFlowDao;
import com.rkylin.risk.core.entity.OperateFlow;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ChenFumin on 2016-9-28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring-test.xml"})
public class OperateFlowDaoTest {

  @Resource
  private OperateFlowDao operateFlowDao;

  @Test
  public void testInsert() {
    OperateFlow operateFlow = new OperateFlow();
    operateFlow.setCourseId("SX002");
    operateFlow.setCourseSecondaryClassify("2");
    operateFlow.setCouserStairClassify("1");
    operateFlow.setCorporationname("北京大学");
    operateFlow.setCorporationId("10002");

    operateFlow.setCheckorderid("checkid001");
    operateFlow.setCustomerid("customerid001");
    operateFlow.setResultstatus("01");
    operateFlow.setReason("Reason");
    operateFlow.setRiskmsg("Riskmsg");
    operateFlow.setRuleid("P10001");
    operateFlow.setClassname("IOS开发");
    operateFlow.setClassprice(new Amount(10000));

    operateFlowDao.insert(operateFlow);
  }

  @Test
  public void testSelect() {
    List<OperateFlow> ofList = operateFlowDao.queryByCheckorderid("checkid001");
    System.out.println(ofList);
  }

}
