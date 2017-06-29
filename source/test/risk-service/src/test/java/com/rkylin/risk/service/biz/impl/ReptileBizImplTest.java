package com.rkylin.risk.service.biz.impl;

import com.Rop.api.domain.Detail;
import com.Rop.api.domain.Result;
import com.rkylin.risk.service.biz.ReptileBiz;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lina on 2016-8-5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
public class ReptileBizImplTest {
  @Resource
  private ReptileBiz reptileBiz;

  @Test
  public void verifyReptileTest() {
    System.out.println(reptileBiz.verifyReptile(1, "吴怀青", "563223198703262111", "18611111111", 3));
  }

  @Test
  public void getReptileListTest() {
    List<Result> results = reptileBiz.getReptileList(new Long(889));
    if (results != null && !results.isEmpty()) {
      for (Result result : results) {
        System.out.println("type:" + result.getType() + ",count:" + result.getCount());
      }
    }
  }

  @Test
  public void getReptileDetail() {
    List<Detail> results = reptileBiz.getReptileDetail(new Long(891), 24);
    if (results != null && !results.isEmpty()) {
      for (Detail detail : results) {
        System.out.println(detail.getDetail());
      }
    }
  }

  @Test
  public void requestVerifyReptileTest() {
    reptileBiz.requestVerifyReptile(2, "郑沃", "356983194609083454", "180222929292", "沃土化肥厂",
        "CER424000202", "5628");
  }

  @Test
  public void pullVerifyResultTest() {
    Map map = reptileBiz.pullVerifyResult("5628", "2");
    Set<String> set = map.keySet();
    for (String key : set) {
      System.out.println(key + ":" + map.get(key));
    }
  }
}
