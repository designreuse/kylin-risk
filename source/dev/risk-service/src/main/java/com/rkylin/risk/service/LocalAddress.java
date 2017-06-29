package com.rkylin.risk.service;

import com.rkylin.risk.service.utils.NetUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tomalloc on 16-11-10.
 */
@Slf4j
public class LocalAddress {
  @Getter
  private String ip = "";

  public LocalAddress(String networkName) {
    if (networkName != null && !(networkName = networkName.trim()).isEmpty()) {
      String localIP = NetUtils.getLocalAddress(networkName.split(","));
      log.info("local ip={}",localIP);
      ip = localIP;
    }
  }
}
