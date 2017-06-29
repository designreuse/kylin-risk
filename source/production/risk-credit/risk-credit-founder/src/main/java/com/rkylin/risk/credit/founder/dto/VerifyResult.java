package com.rkylin.risk.credit.founder.dto;

/**
 * Created by tomalloc on 16-6-30.
 */
public enum VerifyResult {
  /**
   * 成功
   */
  T,
  /**
   * 失败
   */
  F,
  /**
   * 不支持的银行或无法验证
   */
  D
}
