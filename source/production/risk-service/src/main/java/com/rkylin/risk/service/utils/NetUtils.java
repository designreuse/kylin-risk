package com.rkylin.risk.service.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tomalloc on 16-11-10.
 */
@Slf4j
public class NetUtils {

  public static String getLocalAddress(String... networkCard) {
    if (networkCard != null) {
      for (String networkCardName : networkCard) {
        try {
          NetworkInterface networkInterface = NetworkInterface.getByName(networkCardName);
          if (networkInterface == null) {
            continue;
          }
          if (networkInterface.isLoopback() || networkInterface.isPointToPoint() || networkInterface
              .isVirtual() || !networkInterface.isUp()) {
            continue;
          }
          Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
          while (addresses.hasMoreElements()) {
            InetAddress inetAddress = addresses.nextElement();
            if (inetAddress instanceof Inet4Address) {
              return inetAddress.getHostAddress();
            }
          }
        } catch (SocketException e) {
          log.warn("Failed to retriving ip address, " + e.getMessage(), e);
        }
      }
    }
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      log.warn("Failed to retriving ip address, " + e.getMessage(), e);
    }
    return "127.0.0.1";
  }
}
