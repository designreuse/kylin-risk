package com.rkylin.risk.service.credit;

/**
 * Created by tomalloc on 16-12-22.
 */
public interface CreditResultCode {
  /**
   * 未命中目标
   */
  String TARGET_MISSING = "10005";
  /**
   * 命中目标
   */
  String TARGET_HIT = "10000";

  /**
   * 系统错误
   */
  String SYSTEM_ERROR="99999";
}
