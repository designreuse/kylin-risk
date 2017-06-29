package com.rkylin.risk.credit.xml;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-6-28.
 */
@Setter
@Getter
@ToString
public class CreditBaiRongModule {
  private String id;
  private String name;
  private String outputFlag;
  private Map<String, CreditBairRongResultItem> rongResultItemMap;
}
