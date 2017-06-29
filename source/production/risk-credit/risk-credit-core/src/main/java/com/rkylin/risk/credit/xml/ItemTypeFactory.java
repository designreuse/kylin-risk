package com.rkylin.risk.credit.xml;

import com.rkylin.risk.credit.enums.ItemType;
import java.util.Locale;

/**
 * Created by tomalloc on 16-6-28.
 */
public class ItemTypeFactory {
  public static ItemType coast(String item) {
    if (item == null) {
      return null;
    }
    String upperItem = item.toUpperCase(Locale.ENGLISH);
    return ItemType.valueOf(upperItem);
  }
}
