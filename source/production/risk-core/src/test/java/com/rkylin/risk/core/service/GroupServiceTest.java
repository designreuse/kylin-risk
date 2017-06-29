package com.rkylin.risk.core.service;

import com.rkylin.risk.core.BaseTest;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * Created by lina on 2016-12-16.
 */
public class GroupServiceTest extends BaseTest {

  @Resource
  private GroupService groupService;

  @Test
  public void queryByChannelAndStatusTest() {
    System.out.println(groupService.queryByChannelAndServiceType("M000004", "baseinfo"));
  }
}
