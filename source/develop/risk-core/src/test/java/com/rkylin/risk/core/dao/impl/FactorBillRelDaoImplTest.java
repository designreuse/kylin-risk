package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.BaseTest;
import com.rkylin.risk.core.dao.FactorBillRelDao;
import com.rkylin.risk.core.entity.FactorBillRelation;
import com.google.common.collect.Lists;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/12/7 version: 1.0
 */
public class FactorBillRelDaoImplTest extends BaseTest {
  @Resource
  private FactorBillRelDao factorBillRelDao;

  @Test
  public void insertBatchTest() {
    List<FactorBillRelation> factorBillRelationList = Lists.newArrayList();
    int[] sa = {40, 30, 34, 46, 49, 55, 61, 63, 68};
    for (int i = 0; i < sa.length; i++) {
      FactorBillRelation factorBillRelation = new FactorBillRelation();
      factorBillRelation.setBillid("6f9af8df28d947f6bb334d3a41cb250d");
      factorBillRelation.setFactorid(sa[i]);
      factorBillRelation.setFactorscore("12");
      factorBillRelationList.add(factorBillRelation);
    }

    factorBillRelDao.insertBatch(factorBillRelationList);
  }
}
