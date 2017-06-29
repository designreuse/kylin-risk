package com.rkylin.risk.credit.service.report;

import com.rkylin.risk.commons.enumtype.CreditProductType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-8-2.
 */
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class ReportItem {
  private String key;
  private String name;
  private CreditProductType creditProduct;
}
