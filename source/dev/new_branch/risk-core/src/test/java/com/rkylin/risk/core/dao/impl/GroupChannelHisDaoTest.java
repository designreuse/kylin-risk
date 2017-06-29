package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.BaseTest;
import com.rkylin.risk.core.dao.GroupChannelHisDao;
import com.rkylin.risk.core.entity.GroupChannelHis;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * Created by lina on 2016-6-24.
 */
public class GroupChannelHisDaoTest extends BaseTest {
  @Resource
  private GroupChannelHisDao groupChannelHisDao;

  @Test
  public void insertFromRuleChannelTest() {
    GroupChannelHis his = new GroupChannelHis();
    his.setGrouphisid(new Short("9"));
    his.setId(new Short("1"));
    groupChannelHisDao.insertFromGroupChannel(his);
  }
}
