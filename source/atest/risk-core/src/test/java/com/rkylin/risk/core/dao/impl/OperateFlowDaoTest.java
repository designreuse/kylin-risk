package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.dao.OperateFlowDao;
import com.rkylin.risk.core.entity.OperateFlow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  @Test
  public void testUpdateOperFlowStatus() {
    OperateFlow operateFlow = new OperateFlow();
    operateFlow.setResultstatus("3");
    operateFlow.setCheckorderid("7887");
    operateFlow.setReason("这是一个测试原因");
    operateFlowDao.updateOperFlowStatus(operateFlow);
  }

  @Test
  public void mapSelect() {
    Map map = new HashMap();
    map.put("mtApiStatus", "0");
    List<String> list = new ArrayList<>();
    list.add("M000025");
    list.add("M000004");
    map.put("list", list);
    List<OperateFlow> ofList = operateFlowDao.queryByConstidAndStatus(map);
    System.out.println(ofList);
  }
}
