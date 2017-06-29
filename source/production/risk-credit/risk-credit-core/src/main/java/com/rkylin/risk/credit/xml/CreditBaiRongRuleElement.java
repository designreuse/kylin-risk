package com.rkylin.risk.credit.xml;

import com.rkylin.risk.credit.translator.ValueTranslator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomalloc on 16-6-27.
 */
public class CreditBaiRongRuleElement {
  private Map<String, ValueTranslator> translators;

  private List<CreditBaiRongModule> baiRongModules;

  public CreditBaiRongRuleElement() {
    translators = new LinkedHashMap<>();
    baiRongModules = new ArrayList<>();
  }

  /**
   * 添加翻译器
   */
  public void addTranslator(String translatorId, ValueTranslator translator) {
    translators.put(translatorId, translator);
  }

  /**
   * 添加模块
   */
  public void addBaiRongModule(CreditBaiRongModule module) {
    baiRongModules.add(module);
  }

  public boolean existsTranslator(String translatorId) {
    return translators.containsKey(translatorId);
  }

  public ValueTranslator queryTranslator(String translatorId) {
    return translators.get(translatorId);
  }

  /**
   * 根据匹配项的名称查找对应的所属项
   */
  public CreditBairRongResultItem queryModuleItem(String itemId) {
    for (CreditBaiRongModule module : baiRongModules) {
      Map<String, CreditBairRongResultItem> rongResultItemMap = module.getRongResultItemMap();
      if (rongResultItemMap.containsKey(itemId)) {
        return rongResultItemMap.get(itemId);
      }
    }
    return null;
  }
}
