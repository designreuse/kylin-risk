package com.rkylin.risk.credit.service.report;

import java.util.Map;

/**
 * Created by tomalloc on 16-8-2.
 */
public abstract class ChildReportProducer<T> {

  public abstract boolean validateData();

  public abstract String code();

  /**
   * 产生报告
   */
  public abstract Map<ReportItem, ?> run();
}
