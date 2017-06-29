package com.rkylin.risk.core.service;

import com.rkylin.risk.core.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/11 version: 1.0
 */
public class OperatorServiceTest extends BaseTest {
  @Resource
  private OperatorService operatorService;

  @Test
  public void queryOperatorByUsernameTest() {
    operatorService.queryOperatorByUsername("tongzhuyu@crediteasse.cn");
  }
}
