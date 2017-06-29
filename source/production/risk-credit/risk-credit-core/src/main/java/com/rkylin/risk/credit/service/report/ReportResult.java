package com.rkylin.risk.credit.service.report;

import com.rkylin.risk.commons.enumtype.CreditProductType;
import java.util.Map;

/**
 * Created by tomalloc on 16-8-2.
 */
public class ReportResult {
  /**
   * 产品
   */
  private CreditProductType creditProduct;
  /**
   * 模块
   */
  private String module;

  private Map<String, String> items;
}
