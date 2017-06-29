package com.rkylin.risk.service.validator.builder;

import com.rkylin.risk.operation.bean.ResultInfo;

/**
 * Created by tomalloc on 16-4-14.
 */
public class OperationResultInfoBuilder implements Builder<ResultInfo> {
  /**
   * 客户名称
   */
  private String customerName;
  /**
   * 证件号码
   */
  private String certificateId;
  /**
   * 返回码
   */
  private String resultCode;
  /**
   * 返回分数
   */
  private String resultScore;
  /**
   * 返回信息
   */
  private String resultMsg;

  private OperationResultInfoBuilder() {
  }

  public static OperationResultInfoBuilder builder() {
    return new OperationResultInfoBuilder();
  }

  public OperationResultInfoBuilder customerName(String customerName) {
    this.customerName = customerName;
    return this;
  }

  public OperationResultInfoBuilder certificateId(String certificateId) {
    this.certificateId = certificateId;
    return this;
  }

  public OperationResultInfoBuilder resultCode(String resultCode) {
    this.resultCode = resultCode;
    return this;
  }

  public OperationResultInfoBuilder resultScore(String resultScore) {
    this.resultScore = resultScore;
    return this;
  }

  public OperationResultInfoBuilder resultMsg(String resultMsg) {
    this.resultMsg = resultMsg;
    return this;
  }

  public ResultInfo build() {
    ResultInfo resultInfo = new ResultInfo();
    resultInfo.setCustomerName(customerName);
    resultInfo.setCertificateId(certificateId);
    resultInfo.setResultCode(resultCode);
    resultInfo.setResultScore(resultScore);
    resultInfo.setResultMsg(resultMsg);
    return resultInfo;
  }
}
