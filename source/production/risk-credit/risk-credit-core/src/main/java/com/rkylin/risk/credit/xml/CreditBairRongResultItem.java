package com.rkylin.risk.credit.xml;

import com.rkylin.risk.credit.translator.ValueTranslator;
import com.rkylin.risk.credit.enums.ItemType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by tomalloc on 16-6-28.
 */
@Setter
@Getter
@ToString
public class CreditBairRongResultItem {
  private String id;
  private String name;
  private ValueTranslator translator;
  private ItemType itemType;
}
